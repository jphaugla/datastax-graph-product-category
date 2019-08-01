import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';
import GraphContainer from "../Graph";
import InfoPaneContainer from "../InfoPane";

class Index extends React.Component{

    constructor(props){
        super(props)
    }


    render() {
        return (
            <Flexbox flexGrow={1} id="userContainer" flexDirection="row">
                <GraphContainer/>
                <InfoPaneContainer/>
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

const UserContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

UserContainer.propTypes = {
};

export default UserContainer