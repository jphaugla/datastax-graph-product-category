import {UPDATE_GRAPH, UPDATE_GRAPH_DIMENSIONS, UPDATE_INFO_PANE} from "../consts";

const GraphReducer = (state = '', action) => {
    switch (action.type) {
        case UPDATE_GRAPH_DIMENSIONS:

            return {
                ...state,
                width: action.width,
                height: action.height
            }
        case UPDATE_GRAPH:
            return {
                ...state,
                graph: action.graph,
                update_timestamp: new Date().getTime()
            }
        case UPDATE_INFO_PANE:
            return {
                ...state,
                infoPaneNode: action.infoPaneNode
            }
        default:
            return state
    }
}

export default GraphReducer
