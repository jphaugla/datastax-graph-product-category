import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';
import TrainingChart from "./TrainingChart";
import {refreshTrainingData} from "../../actions/AdminActions";
import EventContainer from "./Event";

class Index extends React.Component{

    constructor(props){
        super(props)
    }

    componentWillMount() {
        this.props.loadTrainingData()
    }


    render() {
        return (
            <Flexbox flexGrow={1} id="adminContainer" flexDirection="column">
                {/*<Flexbox id="adminHeader">Admin</Flexbox>*/}

                <Flexbox id="adminContentContainer" flexDirection="row" height="100%">
                    <Flexbox id="trainingChartContainer" width="50%" height="100%" alignItems="center" justifyContent="center" flexDirection="column">
                        <Flexbox id="trainingChartHeader" width="100%" height="50px" flex={0} alignItems="center" justifyContent="center">
                            Model Training Loss
                        </Flexbox>
                        <Flexbox>
                            <TrainingChart trainingData={this.props.trainingData}/>
                        </Flexbox>
                    </Flexbox>
                    <Flexbox id="eventContainer" width="50%" height="100%" flexDirection="column">
                        <Flexbox id="eventHeader" width="100%" height="50px" alignItems="center" justifyContent="center">
                            Latest Prediction
                        </Flexbox>
                        <EventContainer/>
                        <Flexbox flexGrow={1} alignItems="center" justifyContent="center" id="eventRecommendation">
                            {
                                this.props.recommends ? <i className="fas fa-thumbs-up recommend"></i> : <i className="fas fa-thumbs-down dont-recommend"></i>
                            }

                        </Flexbox>
                        <Flexbox flexGrow={1} alignItems="center" justifyContent="center" id="eventTime">
                            {this.props.prediction_time}
                        </Flexbox>
                    </Flexbox>
                </Flexbox>
            </Flexbox>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        trainingData: state.AdminReducer.trainingData,
        recommends: state.AdminReducer.recommends,
        prediction_time: state.AdminReducer.prediction_time
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        loadTrainingData: () => {
            dispatch(refreshTrainingData())
        }
    }
}

const AdminContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(Index)

AdminContainer.propTypes = {
};

export default AdminContainer