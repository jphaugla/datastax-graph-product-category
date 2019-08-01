class InteractionDetails:

    def __init__(
            self,
            user_size,
            product_size,
            feature_size,
            state_change_ratio,
            user_time_diff_sequence,
            product_time_diff_sequence,
            user_previous_product_sequence,
            state_change_sequence,
            user_id_sequence,
            product_id_sequence
    ):
        self.user_size = user_size
        self.product_size = product_size
        self.feature_size = feature_size
        self.state_change_ratio = state_change_ratio
        self.user_time_diff_sequence = user_time_diff_sequence
        self.product_time_diff_sequence = product_time_diff_sequence
        self.user_previous_product_sequence = user_previous_product_sequence
        self.state_change_sequence = state_change_sequence
        self.user_id_sequence = user_id_sequence
        self.product_id_sequence = product_id_sequence
