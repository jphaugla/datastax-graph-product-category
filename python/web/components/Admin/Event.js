import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';

import LineChart from "recharts/es6/chart/LineChart";
import Line from "recharts/es6/cartesian/Line";
import YAxis from "recharts/es6/cartesian/YAxis";
import XAxis from "recharts/es6/cartesian/XAxis";
import Sigma from "react-sigma";
import SigmaLoader from "../Graph/SigmaLoader";
import RandomizeNodePositions from "react-sigma/es/RandomizeNodePositions";
import RelativeSize from "react-sigma/es/RelativeSize";
import {refreshEventGraph, refreshTrainingData} from "../../actions/AdminActions";


class Index extends React.Component{

    constructor(props){
        super(props)
    }

    componentWillMount() {
        this.props.loadEventGraph()
    }

    render() {
       return (
           <Flexbox flexGrow={3} alignItems="center" justifyContent="center" >
                <Sigma
                    renderer="canvas"
                    style={{width:"75%", height:"75%"}}
                    // onOverNode={(e) => {this.props.handleNodeHover(e.data.node)}}
                    settings={
                        {
                            cloning: false,
                            drawEdges: true,
                            drawEdgeLabels: true,
                            defaultNodeColor: "#2DA07E",
                            defaultLabelColor: "#ffffff",
                            minNodeSize: 10
                        }
                    }
                >
                    <SigmaLoader graph={this.props.eventGraph}>
                        <RandomizeNodePositions key={this.props.update_timestamp}>
                          {/*<Filter neighborsOf={ appState.graph.isFiltered ? appState.graph.selectedNode : null } />*/}
                          {/*<ForceAtlas2 barnesHutOptimize barnesHutTheta={0.8} iterationsPerRender={2}/>*/}
                          <RelativeSize initialSize={15}/>
                        </RandomizeNodePositions>

                        {/*<RandomizeNodePositions/>*/}
                        {/*<ForceAtlas2 barnesHutOptimize barnesHutTheta={0.8} iterationsPerRender={2}/>*/}
                        {/*<RelativeSize initialSize={15} />*/}
                    </SigmaLoader>

                </Sigma>
           </Flexbox>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        eventGraph: state.AdminReducer.eventGraph,
        update_timestamp: state.AdminReducer.update_timestamp
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        loadEventGraph: () => {
            dispatch(refreshEventGraph())
        }

    }
}

const EventContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

EventContainer.propTypes = {
};

export default EventContainer