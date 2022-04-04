import React from 'react';
import {Link} from 'react-router-dom';
import {PropTypes} from 'prop-types';

Authentication.propTypes = {
    mode: PropTypes.bool,
    onLogin: PropTypes.func,
    onRegister: PropTypes.func
};

Authentication.defaultProps = {
    mode: true,
    onLogin: (id, pw)=>{console.log("login function not defined")},
    onRegister: (id, pw)=> {console.log("register function not defined")}
};

function Authentication({mode}){
    const loginView = (
        <div>
            <div className="card-content">
                <div className="row">
                    <div className="input-field col s12 username">
                        <label>Username</label>
                        <input
                        name="username"
                        type="text"
                        className="validate"/>
                    </div>
                    <div className="input-field col s12">
                        <label>Password</label>
                        <input
                        name="password"
                        type="password"
                        className="validate"/>
                    </div>
                    <a className="waves-effect waves-light btn">SUBMIT</a>
                </div>
            </div>

            <div className="footer">
                <div className="card-content">
                    <div className="right" >
                    New Here? <Link to="/register">Create an account</Link>
                    </div>
                </div>
            </div>
        </div>
    );

    const registerView = (
        <div className="card-content">
            <div className="row">
                <div className="input-field col s12 username">
                    <label>Username</label>
                    <input
                    name="username"
                    type="text"
                    className="validate"/>
                </div>
                <div className="input-field col s12">
                    <label>Password</label>
                    <input
                    name="password"
                    type="password"
                    className="validate"/>
                </div>
                <a className="waves-effect waves-light btn">CREATE</a>
            </div>
        </div>
    );

    return(
        <div className="container auth">
            <Link className="logo" to="/">WhereAreYou</Link>
            <div className="card">
                <div className="header yellow darken-1 white-text center">
                    <div className="card-content">{mode ? "LOGIN" : "REGISTER"}</div>
                </div>
                {mode ? loginView : registerView }
            </div>
        </div>
    );
};

export default Authentication;