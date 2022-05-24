import React from 'react';
import {Authentication} from '../components';
import { connect } from 'react-redux';
import { registerRequest } from '../actions/authentication';

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.handleRegister = this.handleRegister.bind(this);    
    }
    
    handleRegister(id, pw) {
        return this.props.registerRequest(id, pw).then(
            () => {
                if(this.props.status === "SUCCESS") {
                    console.log('[register request] success. id: ', id);
                    return true;
                } else {
                    /*
                        ERROR CODES:
                            1: BAD USERNAME
                            2: BAD PASSWORD
                            3: USERNAME EXISTS
                    */
                    let errorMessage = [
                        'Invalid Username',
                        'Password is too short',
                        'Username already exists'
                    ];
                    console.log('[register request] fail. id: ', id);
                    console.log('error: ', errorMessage[this.props.errorCode - 1] );
                    return false;
                }
            }
        );
    }

    render() {
        return (
            <Authentication mode={false} onRegister={this.handleRegister}/>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        status: state.authentication.register.status,
        errorCode: state.authentication.register.error
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        registerRequest: (id, pw) => {
            return dispatch(registerRequest(id, pw));
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Register);