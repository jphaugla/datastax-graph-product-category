import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';
import PropTypes from "prop-types";
import UserContainer from "../../User";
import AdminContainer from "../../Admin";

class Index extends React.Component{

    constructor(props){
        super(props)
    }


    render() {
        return (
            <Flexbox flexGrow={1} id="contentContainer" >
                {{
                    "user": <UserContainer/>,
                    "admin": <AdminContainer/>
                }[this.props.currentState]}
            </Flexbox>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        currentState: state.NavReducer.currentState
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
    }
}

const ContentContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

ContentContainer.propTypes = {
    currentState: PropTypes.string
};

export default ContentContainer