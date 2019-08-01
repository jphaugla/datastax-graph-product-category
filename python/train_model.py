import numpy
import torch, datetime
from dse.cluster import Cluster
from torch import nn
from torch.autograd import Variable

import library_models as lib
from torch.nn import functional as F


from interaction import Interaction
from interaction_details import InteractionDetails
from library_models import JODIE


def main():
    cluster = Cluster()
    session = cluster.connect()

    start_time = datetime.datetime.fromtimestamp(1564524000)
    end_time = datetime.datetime.fromtimestamp(1564610400)

    results = []

    current_time = start_time
    while current_time <= end_time:
        rows = session.execute(
            """
            SELECT user_id, product_id, year, month, day, hour, interaction_time, type FROM prodcat.user_interaction_product_history
            WHERE year = %s AND month = %s AND day = %s AND hour = %s
            """,
            (current_time.year, current_time.month, current_time.day, current_time.hour)
        )

        results += rows.current_rows
        current_time += datetime.timedelta(hours=1)

    interactions = parse_interaction_data(results, start_time)
    interaction_details = calc_interaction_details(interactions)
    model = initialize_model(interaction_details)

    train(interactions, interaction_details, model, 150)

def initialize_model(interaction_details):
    embedding_dimensions = 128

    # JODIE
    #   Number of features
    #   Number of users
    #   Number of products
    #   Embedding size
    #   Model Name

    return JODIE(
          interaction_details.feature_size,
          interaction_details.user_size,
          interaction_details.product_size,
          embedding_dimensions,
          "jodie"
      )



def parse_interaction_data(raw_data, model_start_time):
    interactions = []
    products = {}
    users = {}

    product_sequence_id_incr = 0
    user_sequence_id_incr = 0

    user_interaction_count_since_purchase = {}
    for row in raw_data:
        state = row.type == 'True'

        if row.user_id not in users:
            users[row.user_id] = user_sequence_id_incr
            user_sequence_id_incr += 1

            if state:
                user_interaction_count_since_purchase[row.user_id] = 1
            else:
                user_interaction_count_since_purchase[row.user_id] = 0
        else:
            if not state:
                user_interaction_count_since_purchase[row.user_id] += 1

        if row.product_id not in products:
            products[row.product_id] = product_sequence_id_incr
            product_sequence_id_incr += 1


        interaction = Interaction(
            row.user_id,
            users[row.user_id],
            row.product_id,
            0,
            row.interaction_time,
            (row.interaction_time - model_start_time).seconds,
            state,
            [user_interaction_count_since_purchase[row.user_id]]
        )

        if state:
            user_interaction_count_since_purchase[row.user_id] = 0


        interactions.append(interaction)



    return interactions


def calc_interaction_details(interactions):
    users = {}
    products = {}

    user_time_diff_sequence = []
    user_previous_product_sequence = []
    user_id_sequence = []
    user_latest_product = {}
    user_latest_time = {}

    product_time_diff_sequence = []
    product_id_sequence = []
    product_latest_time = {}

    interaction_state_sequence = []

    for interaction in interactions:
        user_id_sequence.append(interaction.user_sequence_id)
        product_id_sequence.append(interaction.product_sequence_id)

        if interaction.user_id not in users:
            users[interaction.user_id] = interaction.user_id
        if interaction.product_id not in products:
            products[interaction.product_id] = interaction.product_sequence_id

        interaction_state_sequence.append(interaction.state)

        # If user id is not in latest time index
        #   Initialize user sequence
        # Else
        #   Calculate delta and store in user time diff sequence

        if interaction.user_id not in user_latest_time:
            user_latest_time[interaction.user_id] = interaction.cardinal_time
            user_time_diff_sequence.append(user_latest_time[interaction.user_id])
        else:
            user_time_diff_sequence.append(interaction.cardinal_time - user_latest_time[interaction.user_id])

        # If product id is not in latest time index
        #   Initialize product sequence
        # Else
        #   Calculate delta and store in product time diff sequence

        if interaction.product_id not in product_latest_time:
            product_latest_time[interaction.product_id] = interaction.cardinal_time
            product_time_diff_sequence.append(product_latest_time[interaction.product_id])
        else:
            product_time_diff_sequence.append(interaction.cardinal_time - product_latest_time[interaction.product_id])

        # If user has not been seen before
        #   Add current product to the latest user product
        #   Add the latest product as the previous product to initialize out sequence.
        # Else
        #   Add the previous product to the previous product sequence
        #   Set the latest product to the newest product.

        if interaction.user_id not in user_latest_product:
            user_latest_product[interaction.user_id] = interaction.product_sequence_id
            user_previous_product_sequence.append(interaction.product_sequence_id)
        else:
            user_previous_product_sequence.append(user_latest_product[interaction.user_id])
            user_latest_product[interaction.user_id] = interaction.product_sequence_id

    products = {'PARENT-ROLL':0}

    return InteractionDetails(
        len(users),
        len(products),
        len(interactions[0].features),
        len(interaction_state_sequence) / (1.0 + sum(interaction_state_sequence)),
        user_time_diff_sequence,
        product_time_diff_sequence,
        user_previous_product_sequence,
        interaction_state_sequence,
        user_id_sequence,
        product_id_sequence
    )

def train(interactions, interaction_details, model, epochs):
    for epoch in range(0, epochs):
        embedding_dimensions = 128

        weight = torch.Tensor([1, interaction_details.state_change_ratio])
        crossEntropyLoss = nn.CrossEntropyLoss(weight=weight)
        MSELoss = nn.MSELoss()

        # TODO: Document and understand more
        initial_user_embedding = nn.Parameter(F.normalize(torch.rand(embedding_dimensions), dim=0))
        initial_item_embedding = nn.Parameter(F.normalize(torch.rand(embedding_dimensions), dim=0))
        model.initial_user_embedding = initial_user_embedding
        model.initial_item_embedding = initial_item_embedding

        # TODO: Document and understand more
        user_embeddings = initial_user_embedding.repeat(interaction_details.user_size, 1)
        item_embeddings = initial_item_embedding.repeat(interaction_details.product_size, 1)
        item_embedding_static = Variable(torch.eye(interaction_details.product_size))
        user_embedding_static = Variable(torch.eye(interaction_details.user_size))

        # TODO: Document and understand more
        learning_rate = 1e-3
        optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate, weight_decay=1e-5)

        user_embeddings_timeseries = Variable(torch.Tensor(len(interactions), embedding_dimensions))
        item_embeddings_timeseries = Variable(torch.Tensor(len(interactions), embedding_dimensions))

        optimizer.zero_grad()
        lib.reinitialize_tbatches()
        total_loss, loss, total_interaction_count = 0, 0, 0

        tbatch_start_time = None
        tbatch_to_insert = -1
        tbatch_full = False

        tbatch_timespan = 1000

        for i, interaction in enumerate(interactions):
            # READ INTERACTION J
            user_sequence_id = interaction.user_sequence_id
            product_sequence_id = interaction.product_sequence_id
            feature = interaction.features

            user_time_diff = interaction_details.user_time_diff_sequence[i]
            product_time_diff = interaction_details.product_time_diff_sequence[i]

            # CREATE T-BATCHES: ADD INTERACTION J TO THE CORRECT T-BATCH
            tbatch_to_insert = max(lib.tbatchid_user[user_sequence_id], lib.tbatchid_item[product_sequence_id]) + 1
            lib.tbatchid_user[user_sequence_id] = tbatch_to_insert
            lib.tbatchid_item[product_sequence_id] = tbatch_to_insert

            lib.current_tbatches_user[tbatch_to_insert].append(user_sequence_id)
            lib.current_tbatches_item[tbatch_to_insert].append(product_sequence_id)
            lib.current_tbatches_feature[tbatch_to_insert].append(feature)
            lib.current_tbatches_interactionids[tbatch_to_insert].append(i)
            lib.current_tbatches_user_timediffs[tbatch_to_insert].append(user_time_diff)
            lib.current_tbatches_item_timediffs[tbatch_to_insert].append(product_time_diff)
            lib.current_tbatches_previous_item[tbatch_to_insert].append(interaction_details.user_previous_product_sequence[i])

            timestamp = interaction.cardinal_time
            if tbatch_start_time is None:
                tbatch_start_time = timestamp

            # # AFTER ALL INTERACTIONS IN THE TIMESPAN ARE CONVERTED TO T-BATCHES, FORWARD PASS TO CREATE EMBEDDING TRAJECTORIES AND CALCULATE PREDICTION LOSS
            if timestamp - tbatch_start_time > tbatch_timespan:
                tbatch_start_time = timestamp  # RESET START TIME FOR THE NEXT TBATCHES

                for j in range(0, len(lib.current_tbatches_user)):
                    total_interaction_count += len(lib.current_tbatches_interactionids[j])

                    # LOAD THE CURRENT TBATCH
                    tbatch_userids = torch.LongTensor(
                        lib.current_tbatches_user[j])  # Recall "lib.current_tbatches_user[i]" has unique elements
                    tbatch_itemids = torch.LongTensor(lib.current_tbatches_item[j])  # Recall "lib.current_tbatches_item[i]" has unique elements
                    tbatch_interactionids = torch.LongTensor(lib.current_tbatches_interactionids[j])
                    feature_tensor = Variable(torch.Tensor(lib.current_tbatches_feature[j]))  # Recall "lib.current_tbatches_feature[i]" is list of list, so "feature_tensor" is a 2-d tensor
                    user_timediffs_tensor = Variable(torch.Tensor(lib.current_tbatches_user_timediffs[j])).unsqueeze(1)
                    item_timediffs_tensor = Variable(torch.Tensor(lib.current_tbatches_item_timediffs[j])).unsqueeze(1)
                    tbatch_itemids_previous = torch.LongTensor(lib.current_tbatches_previous_item[j])
                    item_embedding_previous = item_embeddings[tbatch_itemids_previous, :]

                    # PROJECT USER EMBEDDING TO CURRENT TIME
                    user_embedding_input = user_embeddings[tbatch_userids, :]
                    user_projected_embedding = model.forward(user_embedding_input, item_embedding_previous,
                                                             timediffs=user_timediffs_tensor, features=feature_tensor,
                                                             select='project')
                    user_item_embedding = torch.cat([user_projected_embedding, item_embedding_previous,
                                                     item_embedding_static[tbatch_itemids_previous, :],
                                                     user_embedding_static[tbatch_userids, :]], dim=1)

                    # PREDICT NEXT ITEM EMBEDDING
                    predicted_item_embedding = model.predict_item_embedding(user_item_embedding)

                    # CALCULATE PREDICTION LOSS
                    item_embedding_input = item_embeddings[tbatch_itemids, :]
                    loss += MSELoss(predicted_item_embedding,
                                    torch.cat([item_embedding_input, item_embedding_static[tbatch_itemids, :]],
                                              dim=1).detach())

                    # UPDATE DYNAMIC EMBEDDINGS AFTER INTERACTION
                    user_embedding_output = model.forward(user_embedding_input, item_embedding_input,
                                                          timediffs=user_timediffs_tensor, features=feature_tensor,
                                                          select='user_update')
                    item_embedding_output = model.forward(user_embedding_input, item_embedding_input,
                                                          timediffs=item_timediffs_tensor, features=feature_tensor,
                                                          select='item_update')

                    item_embeddings[tbatch_itemids, :] = item_embedding_output
                    user_embeddings[tbatch_userids, :] = user_embedding_output

                    user_embeddings_timeseries[tbatch_interactionids, :] = user_embedding_output
                    item_embeddings_timeseries[tbatch_interactionids, :] = item_embedding_output

                    # CALCULATE LOSS TO MAINTAIN TEMPORAL SMOOTHNESS
                    loss += MSELoss(item_embedding_output, item_embedding_input.detach())
                    loss += MSELoss(user_embedding_output, user_embedding_input.detach())

                    # CALCULATE STATE CHANGE LOSS
                    # if args.state_change:
                    loss += lib.calculate_state_prediction_loss(model, tbatch_interactionids, user_embeddings_timeseries, interaction_details.state_change_sequence, crossEntropyLoss)

                # BACKPROPAGATE ERROR AFTER END OF T-BATCH
                total_loss += loss.item()
                loss.backward()
                optimizer.step()
                optimizer.zero_grad()

                # RESET LOSS FOR NEXT T-BATCH
                loss = 0
                item_embeddings.detach_()  # Detachment is needed to prevent double propagation of gradient
                user_embeddings.detach_()
                item_embeddings_timeseries.detach_()
                user_embeddings_timeseries.detach_()

                # REINITIALIZE
                lib.reinitialize_tbatches()
                tbatch_to_insert = -1

        print("\n\nTotal loss in this epoch = %f" % (total_loss))
        item_embeddings_dystat = torch.cat([item_embeddings, item_embedding_static], dim=1)
        user_embeddings_dystat = torch.cat([user_embeddings, user_embedding_static], dim=1)
            # SAVE CURRENT MODEL TO DISK TO BE USED IN EVALUATION.
        lib.save_model(model, optimizer, "product", "jodie", epoch, user_embeddings_dystat, item_embeddings_dystat, i, total_loss,
                       user_embeddings_timeseries, item_embeddings_timeseries)

            # user_embeddings = initial_user_embedding.repeat(num_users, 1)
            # item_embeddings = initial_item_embedding.repeat(num_items, 1)

main()