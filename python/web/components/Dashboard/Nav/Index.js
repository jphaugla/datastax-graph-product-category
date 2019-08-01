import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';
import PropTypes from "prop-types";
import NavButton from "./NavButton";
import {changeNavState, togglePanel} from "../../../actions/NavActions";
import {search, selectResult} from "../../../actions/SearchActions";

class Index extends React.Component{

    constructor(props){
        super(props)
    }


    render() {
        return (
            <Flexbox id="navContainer"  flexGrow="0">
                <Flexbox flexGrow="1"  justifyContent="flex-start" id="searchContainer" flexDirection="column">
                    {{
                        "user": <input onChange={(e) => {this.props.handleQueryChange(e.target.value)}} id="searchBox" placeholder="Search for Products"/>
                    }[this.props.currentState]}

                    {
                        this.props.searchResults.length !== 0 ?
                        <ul id="searchResults">
                            {
                                this.props.searchResults.map((value, index) => {
                                    return <li key={index} onClick={() => {this.props.handleResultSelection(value.prod_id, value.model_name, value.high_pic)}}>
                                        <div>{value.name}</div>
                                    </li>
                                })
                            }
                        </ul> : null
                    }

                </Flexbox>
                <Flexbox justifyContent="flex-end" flexGrow="1">
                    <Flexbox
                        id="userBtn"
                        justifyContent="center"
                        alignItems="center"
                        onClick={() => {this.props.navBtnClick("user")}}
                        className={this.props.currentState === "user" ? "active": ""}
                    >
                        User
                    </Flexbox>
                    <Flexbox
                        id="adminBtn"
                        justifyContent="center"
                        alignItems="center"
                        onClick={() => {this.props.navBtnClick("admin")}}
                        className={this.props.currentState === "admin" ? "active": ""}
                    >
                        Admin
                    </Flexbox>
                </Flexbox>

            </Flexbox>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        currentState: state.NavReducer.currentState,
        searchResults: state.SearchReducer.searchResults
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        navBtnClick: (state) => {
            dispatch(changeNavState(state))
        },
        handleQueryChange: (query) => {
            dispatch(search(query))
        },
        handleResultSelection: (prod_id, model_name, high_pic) => {
            dispatch(selectResult(prod_id, model_name, high_pic))
        }
    }
}

const NavContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

NavContainer.propTypes = {
    currentState: PropTypes.string
};

export default NavContainer