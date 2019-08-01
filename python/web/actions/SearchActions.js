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
import {SEARCH} from "../consts";
import axios from "axios";
import {updateGraph} from "./GraphActions";

export function search(query) {
    return (dispatch, getState) => {
        if (query.length >= 3) {
            axios
                .get("/search?query=" + query)
                .then(function (response) {
                    dispatch({
                        type: SEARCH,
                        searchResults: response.data
                    })
                })
        } else {
            dispatch({
                type: SEARCH,
                searchResults: []
            })
        }

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