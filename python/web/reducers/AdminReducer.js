import {UPDATE_EVENT_GRAPH, UPDATE_TRAINING_DATA} from "../consts";

const AdminReducer = (state = '', action) => {
    switch (action.type) {
        case UPDATE_TRAINING_DATA:
            return {
                ...state,
                trainingData: action.trainingData
            }
        case UPDATE_EVENT_GRAPH:
            return {
                ...state,
                eventGraph: action.response.graph,
                prediction_time: action.response.prediction_time,
                recommends: action.response.recommends,
                recommendation: action.response.recommendation,
                update_timestamp: new Date().getTime()
            }
        default:
            return state
    }
}

export default AdminReducer
