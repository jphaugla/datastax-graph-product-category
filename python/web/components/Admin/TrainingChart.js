import React from 'react'
import { connect } from 'react-redux'

import Flexbox from 'flexbox-react';

import LineChart from "recharts/es6/chart/LineChart";
import Line from "recharts/es6/cartesian/Line";
import YAxis from "recharts/es6/cartesian/YAxis";
import XAxis from "recharts/es6/cartesian/XAxis";


class TrainingChart extends React.Component{

    constructor(props){
        super(props)
    }


    render() {
		const data = [{name: 'Page A', uv: 400, pv: 2400, amt: 2400}];
       return (
              <LineChart  width={500} height={500} margin={{top: 20, right: 20, bottom: 20, left: 20}} data={this.props.trainingData}>
				<Line type="monotone" dataKey="uv" dot={false} stroke="#ffffff" />
				<XAxis dataKey="name" stroke="#ffffff"/>
                <YAxis stroke="#ffffff"/>
			  </LineChart>
        );
    }
}


TrainingChart.propTypes = {
};

export default TrainingChart