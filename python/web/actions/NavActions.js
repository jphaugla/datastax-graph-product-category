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

import {CHANGE_NAV_STATE} from "../consts";

export function changeNavState(state) {
    return {
        type: CHANGE_NAV_STATE,
        state: state
    }
}
