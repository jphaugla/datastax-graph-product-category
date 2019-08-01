import numpy
import torch, datetime
from dse.cluster import Cluster
from sklearn.metrics import roc_auc_score
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

    for interaction in interactions[:50]:
        predictions = evaluate_model(interaction, interaction_details, model, 18)

        print("Predicted Product: %s, Actual: %s", predictions[0], interaction.product_sequence_id)
        print("Predicted State Change: %s, Actual: %s", predictions[1], interaction.state)
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

    test = 0
    for row in raw_data:
        state = row.type == 'True'

        if row.user_id not in users:
            users[row.user_id] = user_sequence_id_incr
            user_sequence_id_incr += 1

            if state:
                user_interaction_count_since_purchase[row.user_id] = 0
            else:
                user_interaction_count_since_purchase[row.user_id] = 1
        else:
            if state:
                user_interaction_count_since_purchase[row.user_id] = 0
            else:
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
        interactions.append(interaction)
        test += 1


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

def evaluate_model(interaction, interaction_details, model, epoch):
    embedding_dimensions = 128
    loss = 0

    weight = torch.Tensor([1, interaction_details.state_change_ratio])
    crossEntropyLoss = nn.CrossEntropyLoss(weight=weight)
    MSELoss = nn.MSELoss()

    # TODO: Document and understand more
    learning_rate = 1e-3
    optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate, weight_decay=1e-5)

    model, optimizer, user_embeddings_dystat, item_embeddings_dystat, user_embeddings_timeseries, item_embeddings_timeseries, train_end_idx_training, start_epoch = lib.load_model(
        model, optimizer, "jodie", "product", epoch)

    lib.set_embeddings(user_embeddings_dystat, item_embeddings_dystat, user_embeddings_timeseries,
                                    item_embeddings_timeseries, interaction)

    item_embeddings = item_embeddings_dystat[:, :embedding_dimensions]
    item_embeddings = item_embeddings.clone()
    item_embeddings_static = item_embeddings_dystat[:, embedding_dimensions:]
    item_embeddings_static = item_embeddings_static.clone()

    user_embeddings = user_embeddings_dystat[:, :embedding_dimensions]
    user_embeddings = user_embeddings.clone()
    user_embeddings_static = user_embeddings_dystat[:, embedding_dimensions:]
    user_embeddings_static = user_embeddings_static.clone()

    userid = interaction.user_sequence_id
    itemid = interaction.product_sequence_id
    feature = interaction.features

    # TODO: Modify to calculate time diff based off start time for both interaction and product
    user_timediff = interaction.cardinal_time
    item_timediff = interaction.cardinal_time
    timestamp = interaction.cardinal_time

    tbatch_start_time = None
    if not tbatch_start_time:
        tbatch_start_time = timestamp

    itemid_previous = interaction.product_sequence_id

    # LOAD USER AND ITEM EMBEDDING
    user_embedding_input = user_embeddings[torch.LongTensor([userid])]
    user_embedding_static_input = user_embeddings_static[torch.LongTensor([userid])]
    item_embedding_input = item_embeddings[torch.LongTensor([itemid])]
    item_embedding_static_input = item_embeddings_static[torch.LongTensor([itemid])]

    feature_tensor = Variable(torch.Tensor(feature)).unsqueeze(0)
    user_timediffs_tensor = Variable(torch.Tensor([user_timediff])).unsqueeze(0)
    item_timediffs_tensor = Variable(torch.Tensor([item_timediff])).unsqueeze(0)
    item_embedding_previous = item_embeddings[torch.LongTensor([itemid_previous])]

    # PROJECT USER EMBEDDING
    user_projected_embedding = model.forward(user_embedding_input, item_embedding_previous,
                                             timediffs=user_timediffs_tensor, features=feature_tensor, select='project')
    user_item_embedding = torch.cat(
        [user_projected_embedding, item_embedding_previous, item_embeddings_static[torch.LongTensor([itemid_previous])],
         user_embedding_static_input], dim=1)

    # PREDICT ITEM EMBEDDING
    predicted_item_embedding = model.predict_item_embedding(user_item_embedding)

    # CALCULATE PREDICTION LOSS
    loss += MSELoss(predicted_item_embedding,
                    torch.cat([item_embedding_input, item_embedding_static_input], dim=1).detach())

    # CALCULATE DISTANCE OF PREDICTED ITEM EMBEDDING TO ALL ITEMS
    product_euclidean_distances = nn.PairwiseDistance()(predicted_item_embedding.repeat(interaction_details.product_size, 1),
                                                torch.cat([item_embeddings, item_embeddings_static], dim=1)).squeeze(-1)

    # CALCULATE RANK OF THE TRUE ITEM AMONG ALL ITEMS
    product_true_item_distance = product_euclidean_distances[itemid]
    product_euclidean_distances_smaller = (product_euclidean_distances < product_true_item_distance).data.cpu().numpy()
    product_true_item_rank = numpy.sum(product_euclidean_distances_smaller) + 1


    # if j < test_start_idx:
    #     validation_ranks.append(true_item_rank)
    # else:
    #     test_ranks.append(true_item_rank)

    # UPDATE USER AND ITEM EMBEDDING
    user_embedding_output = model.forward(user_embedding_input, item_embedding_input, timediffs=user_timediffs_tensor,
                                          features=feature_tensor, select='user_update')
    item_embedding_output = model.forward(user_embedding_input, item_embedding_input, timediffs=item_timediffs_tensor,
                                          features=feature_tensor, select='item_update')

    probabilities = model.predict_label(user_embedding_output).data.cpu().numpy()

    predicted_state_change = 0
    for i in range(0, len(probabilities[0])):

        prob = probabilities[0][i]
        if prob > probabilities[0][predicted_state_change]:
            predicted_state_change = i

    #
    # # SAVE EMBEDDINGS
    # item_embeddings[itemid, :] = item_embedding_output.squeeze(0)
    # user_embeddings[userid, :] = user_embedding_output.squeeze(0)
    # user_embeddings_timeseries[j, :] = user_embedding_output.squeeze(0)
    # item_embeddings_timeseries[j, :] = item_embedding_output.squeeze(0)
    #
    # # CALCULATE LOSS TO MAINTAIN TEMPORAL SMOOTHNESS
    # loss += MSELoss(item_embedding_output, item_embedding_input.detach())
    # loss += MSELoss(user_embedding_output, user_embedding_input.detach())
    #
    # # CALCULATE STATE CHANGE LOSS
    # loss += lib.calculate_state_prediction_loss(model, [j], user_embeddings_timeseries, interaction_details.state_change_sequence, crossEntropyLoss)

    # # UPDATE THE MODEL IN REAL-TIME USING ERRORS MADE IN THE PAST PREDICTION
    # if timestamp - tbatch_start_time > tbatch_timespan:
    #     tbatch_start_time = timestamp
    #     loss.backward()
    #     optimizer.step()
    #     optimizer.zero_grad()
    #
    #     # RESET LOSS FOR NEXT T-BATCH
    #     loss = 0
    #     item_embeddings.detach_()
    #     user_embeddings.detach_()
    #     item_embeddings_timeseries.detach_()
    #     user_embeddings_timeseries.detach_()

    return [product_true_item_rank, predicted_state_change]


main()