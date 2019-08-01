import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';
import GraphContainer from "../Graph";
import {generateInteraction, updateInfoPane} from "../../actions/GraphActions";

class Index extends React.Component{

    constructor(props){
        super(props)
    }


    render() {
        return (
            <Flexbox flexGrow={1} id="infoPaneContainer" flexDirection="column">
                {console.log(this.props.infoPaneNode)}
                <Flexbox flexGrow={4} flexDirection="column">
                    <Flexbox id="infoPaneHeader">
                        Product
                    </Flexbox>
                    <Flexbox id="infoPaneImage" justifyContent="center" alignItems="center" >
                        <img src={this.props.infoPaneNode.high_pic}/>
                    </Flexbox>
                    <Flexbox id="infoPaneModelName" >
                        {this.props.infoPaneNode.model_name}
                    </Flexbox>
                </Flexbox>

                <Flexbox flexGrow={1} id="infoPaneFooter" flexDirection="row" alignItems="flex-end" justifyContent="center">
                    <Flexbox id="viewInteractionBtn"
                             alignItems="center"
                             justifyContent="center"
                             onClick={() => {
                                 this.props.handleGenerateInteraction(this.props.infoPaneNode,0)
                             }}>
                        View
                    </Flexbox>
                    <Flexbox id="purchaseInteractionBtn"
                             alignItems="center"
                             justifyContent="center"
                             onClick={() => {
                                 this.props.handleGenerateInteraction(this.props.infoPaneNode, 1)
                             }}>
                        Purchase
                    </Flexbox>
                </Flexbox>
            </Flexbox>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        infoPaneNode: state.GraphReducer.infoPaneNode
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        handleGenerateInteraction: (product, state) => {
            dispatch(generateInteraction(product.id, state))
        }
    }
}

const InfoPaneContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

InfoPaneContainer.propTypes = {
};

export default InfoPaneContainer