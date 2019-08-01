// Example:
// export function action(key) {
//     return {
//         type: TYPE,
//     }
// }
// export function thunkAction(){
//     return (dispatch, getState) => {
//
//     }
// }
import {UPDATE_GRAPH_DIMENSIONS, UPDATE_GRAPH, UPDATE_INFO_PANE, SEARCH} from "../consts";
import axios from "axios";

export function updateGraphDimensions(width, height) {
    return {
        type: UPDATE_GRAPH_DIMENSIONS,
        width: width,
        height: height
    }
}

export function updateGraph(graph) {
    return {
        type: UPDATE_GRAPH,
        graph: graph
    }
}

export function updateInfoPane(infoPaneNode) {
    return {
        type: UPDATE_INFO_PANE,
        infoPaneNode: infoPaneNode
    }
}

export function generateInteraction(prod_id, state) {
    console.log(prod_id)
    return (dispatch, getState) => {
        axios
            .get("/model/evaluate?prod_id=" + prod_id + "&state=" + state )
            .then(function (response) {
                console.log(response)
                // dispatch(updateGraph(response.data))
            })
        // dispatch({
        //     type: SEARCH,
        //     searchResults: []
        // })
    }
}