import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';
import {updateGraphDimensions, updateInfoPane} from "../../actions/GraphActions";
import Sigma from "react-sigma";
import RelativeSize from "react-sigma/es/RelativeSize";
import RandomizeNodePositions from "react-sigma/es/RandomizeNodePositions";
import SigmaLoader from "./SigmaLoader";
import ForceAtlas2 from "react-sigma/es/ForceAtlas2";
import {selectResult} from "../../actions/SearchActions";


class Index extends React.Component{

    constructor(props){
        super(props)
        this.init = this.init.bind(this)
    }
    componentWillMount() {
        this.init()
    }
    init() {

        // let graph = {
        //     nodes: [],
        //     edges: []
        // }
        //
        // let data = this.props.vertices

        // = {nodes:[{id:"n1", label:"Alice"}, {id:"n2", label:"Rabbit"}], edges:[{id:"e1",source:"n1",target:"n2",label:"SEES"}]};
        // let g = this.props.graph;
        // let data = this.props.vertices;
        // // Handle Account Vertex first in the event that the key list is not sorted
        // let accountVertex = data["0"];
        // Object.keys(data).forEach(function(vKey, i) {
        //     let vertex = data[vKey]
        //     graph.nodes.push({
        //         "id": vertex.id,
        //         "label": vertex.label
        //     })
        //
        //    vertex.edges.forEach(function(edge) {
        //         graph.edges.push({
        //             "id": edge.id,
        //             "source": edge.src,
        //             "target": edge.dst,
        //             "label": edge.label
        //         })
        //     })
        // });
        //
        // this.graph = graph

        // accountVertex.edges.forEach(function(edge, i) {
        //     g.edges.push({
        //        id: edge.id,
        //        source: edge.src,
        //        target: edge.dst
        //    })
        // });
        //
        //
        // Object.keys(data).forEach(function(key, i) {
        //     if(key !== "0"){
        //         let vertex = data[key];
        //         g.nodes.push({
        //             id: vertex.id,
        //             // x: 1,
        //             // y: 1,
        //             size: 1,
        //             color: '#f00'
        //         })
        //     }
        // })
        //
        // this.sigma = new window.sigma({
        //     graph: g,
        //     renderer: {
        //         container: document.getElementById('graphModuleContainer'),
        //         type: 'canvas',
        //     },
        //     settings: {
        //         enableCamera: false
        //     },
        // })
        //
        // var config = {
        //   nodeMargin: 3.0,
        //   scaleNodes: 1.3
        // };
        //
        // console.log(this.sigma)
        //
        // var listener = this.sigma.configNoverlap(config);
        //
        // // Bind all events:
        // listener.bind('start stop interpolate', function(event) {
        //   console.log(event.type);
        // });
        //
        // // Start the algorithm:
        // this.sigma.startNoverlap();
        //
        // this.graph = g

    }

    render() {
        return (
            <Flexbox id="graphModuleContainer" flexGrow={5} flexDirection="column">
                <Sigma
                    renderer="canvas"
                    style={{width:"100%", height:"100%"}}
                    onOverNode={(e) => {this.props.handleNodeHover(e.data.node)}}
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
                    <SigmaLoader graph={this.props.graph}>
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
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        graphWidth: state.GraphReducer.width,
        graphHeight: state.GraphReducer.height,
        graph: state.GraphReducer.graph,
        update_timestamp: state.GraphReducer.update_timestamp
        // vertices: {
        //     "0": {
        //         id: "0",
        //         label: "user",
        //         edges: [{
        //             id: "1",
        //             label: "interacted",
        //             src: "0",
        //             dst: "1"
        //         }]
        //     },
        //     "1": {
        //         id: "1",
        //         label: "product",
        //         edges: []
        //     },
        // },
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        handleNodeHover: (node) => {
            dispatch(updateInfoPane(node))
        }
    }
}

const GraphContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

GraphContainer.propTypes = {
};

export default GraphContainer