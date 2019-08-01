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
import {UPDATE_TRAINING_DATA, UPDATE_EVENT_GRAPH} from "../consts";
import axios from "axios";
import {updateGraph} from "./GraphActions";

export function refreshTrainingData() {
    return (dispatch, getState) => {

        axios
            .get("/trainingData")
            .then(function (response) {
                dispatch({
                    type: UPDATE_TRAINING_DATA,
                    trainingData: response.data
                })
            })

    }
}

export function refreshEventGraph() {
    return (dispatch, getState) => {

        axios
            .get("/eventGraph")
            .then(function (response) {
                dispatch({
                    type: UPDATE_EVENT_GRAPH,
                    response: response.data
                })
            })

    }
}

export function selectResult(prod_id, model_name, high_pic) {
    return (dispatch, getState) => {
        axios
            .get("/product?prod_id=" + prod_id + "&model_name=" + model_name + "&high_pic=" + high_pic)
            .then(function (response) {
                dispatch(updateGraph(response.data))
            })
        dispatch({
            type: SEARCH,
            searchResults: []
        })
    }
}