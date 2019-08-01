import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';
import NavContainer from "./Nav";
import ContentContainer from "./Content";

class Index extends React.Component{

    constructor(props){
        super(props)
    }


    render() {
        return (
            <Flexbox minHeight="100vh" minWidth="100vw" id="dashboardContainer" flexDirection="column">
                <NavContainer/>
                <ContentContainer/>
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

const DashboardContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

DashboardContainer.propTypes = {
};

export default DashboardContainer