import {CHANGE_NAV_STATE, TOGGLE_PANEL} from "../consts";

const NavReducer = (state = '', action) => {
    switch (action.type) {
        case CHANGE_NAV_STATE:
            return {
                ...state,
                currentState: action.state
            }
        default:
            return state
    }
}

export default NavReducer
