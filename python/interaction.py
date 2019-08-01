class Interaction:

    def __init__(self, user_id, user_sequence_id, product_id, product_sequence_id, interaction_time, cardinal_time, state, features):
        self.user_id = user_id
        self.user_sequence_id = user_sequence_id
        self.product_id = product_id
        self.product_sequence_id = product_sequence_id
        self.interaction_time = interaction_time
        self.cardinal_time = cardinal_time
        self.state = state
        self.features = features
