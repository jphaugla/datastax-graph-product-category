import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import Reducers from './reducers'

import RootContainer from './components/Root'

import thunkMiddleware from 'redux-thunk'

import injectTapEventPlugin from 'react-tap-event-plugin'
injectTapEventPlugin();

var initialState = {
    AdminReducer: {
        trainingData: [],
        eventGraph: {
            "edges": [],
            "nodes": []
        },
        prediction_time: 0,
        recommendation: -1,
        recommends: false,
        update_timestamp: 0
    },
    GraphReducer: {
        height: 0,
        width: 0,
        graph: {
            "edges": [],
            "nodes": []
        },
        update_timestamp: 0,
        infoPaneNode: {
            high_pic: "",
            model_name: "",
            label: "",
            prod_id: ""
        }

    },
    RootReducer: {

    },
    NavReducer: {
        currentState: "user",
    },
    SearchReducer: {
        query: "",
        searchResults: []
    }
}

let store = createStore(
    Reducers,
    initialState,
    applyMiddleware(
        thunkMiddleware,
    )
)

render(
    <Provider store={store}>
        <RootContainer />
    </Provider>,
    document.getElementById('root')
)