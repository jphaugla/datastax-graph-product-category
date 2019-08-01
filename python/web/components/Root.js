import React from 'react'
import { connect } from 'react-redux'
import '../scss/Root.scss'

import Flexbox from 'flexbox-react';
import PropTypes from "prop-types";

import DashboardContainer from './Dashboard';

class Root extends React.Component{

    constructor(props){
        super(props)
    }

    componentWillMount() {
    }

    render() {
        return (
            <Flexbox minHeight="100vh" minWidth="100vw" id="rootContainer">
                <DashboardContainer/>
            </Flexbox>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
    }
}

const RootContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Root)

RootContainer.propTypes = {
};

export default RootContainer