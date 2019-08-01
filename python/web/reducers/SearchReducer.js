import {SEARCH} from "../consts";

const SearchReducer = (state = '', action) => {
    switch (action.type) {
        case SEARCH:
            return {
                ...state,
                searchResults: action.searchResults
            }
        default:
            return state
    }
}

export default SearchReducer
