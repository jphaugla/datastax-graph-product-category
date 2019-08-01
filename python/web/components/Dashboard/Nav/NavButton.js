import React from 'react'

import Flexbox from 'flexbox-react';
import PropTypes from "prop-types";

class NavButton extends React.Component {

    constructor(props){
        super(props)
    }
    componentWillMount() {
    }
    render() {
        return (
            <Flexbox width="45px" height="45px" flexDirection="row">
                <Flexbox
                    height="100%"
                    flex="1 1 auto"
                    justifyContent="center"
                    alignItems="center"
                    className={this.props.btnClass}
                    onClick={() => {this.props.navBtnClick(this.props.page)}}
                >
                    {
                        this.props.src !== undefined ?
                            <img
                                src={this.props.src}
                                className={this.props.iconClass}
                            /> :
                            <span
                                className={"fas fa-" + this.props.icon + " " + this.props.iconClass}
                            />
                    }
                </Flexbox>
            </Flexbox>
        )
    }
}

NavButton.propTypes = {
    icon: PropTypes.string,
    src: PropTypes.string,
    btnClass: PropTypes.string,
    iconClass: PropTypes.string,
    currentState: PropTypes.string,
    navBtnClick: PropTypes.func,
};
export default NavButton