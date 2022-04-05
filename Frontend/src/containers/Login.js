import React from 'react';
import {Authentication} from '../components';
import {connect} from 'react-redux';
import {loginRequest} from '../actions/authentication';

function Login(){
    const onLoginReguest = (e, id, pw)=>{
        return loginRequest(id, pw).then(
            ()=> {
                if(e.status === 'SUCCESS'){
                    let loginData = {
                        isLoggedIn: true,
                        username: id
                    };

                    document.cookie = 'key=' + btoa(JSON.stringify(loginData));

                    return true;
                }
                return false;
            }
        );
    };

    return (
        <div>
            <Authentication mode={true} onLogin={onLoginReguest}/>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        status: state.authentication.login.status
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        loginRequest: (id, pw) => { 
            return dispatch(loginRequest(id,pw)); 
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);