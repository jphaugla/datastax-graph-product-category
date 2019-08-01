import { combineReducers } from 'redux'
import RootReducer from './RootReducer';
import NavReducer from './NavReducer';
import GraphReducer from "./GraphReducer";
import SearchReducer from "./SearchReducer";
import AdminReducer from "./AdminReducer";

const Reducers = combineReducers({
    AdminReducer,
    RootReducer,
    NavReducer,
    GraphReducer,
    SearchReducer
});

export default Reducers