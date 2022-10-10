import React, {useState} from 'react';
import { Link } from 'react-router-dom';
import { PropTypes } from 'prop-types';

Authentication.propTypes = {
    mode: PropTypes.bool,
    onLogin: PropTypes.func,
    onRegister: PropTypes.func
};

Authentication.defaultProps = {
    mode: true,
    onLogin: (id, pw) => { console.log("login function not defined") },
    onRegister: (userData) => { console.log("register function not defined") }
};

function Authentication({ mode, onLogin, onRegister }) {
    const [userData, setData] = useState({ name: '', pw: '', id: '' });

    const onNameChange = (e) => {
        setData((prevState) => { return { ...prevState, name: e.target.value } });
        console.log(userData);
    };

    const onPwChange = (e) => {
        setData((prevState) => { return { ...prevState, pw: e.target.value } });
    }

    const onIdChange = (e) => {
        setData((prevState) => { return { ...prevState, id: e.target.value } });
    }

    const kakaoLogin = (e) => {
        // kakao login
        if (!window.Kakao.isInitialized()) {
            window.Kakao.init('283559fa033c8cf5d3a8289adcc25596');
            console.log('kakao init: ', window.Kakao.isInitialized());
        }
        window.Kakao.Auth.login({
            success: function (response) {
                window.Kakao.API.request({
                    url: '/v2/user/me',
                    success: function (response) {
                        console.log("success: ", response);
                    },
                    fail: function (error) {
                        console.log(error)
                    },
                })
            },
            fail: function (error) {
                console.log(error)
            },
        })
    }

    const HandleLogin = (e) => {
        onLogin(userData.id, userData.pw).then(
            (success) => {
                if (!success) {
                    setData(() => { return { id: '', pw: '', name: '' } });
                    console.log(userData);
                }
            }
        )
    };

    const HandleRegister = (e) => {
        onRegister(userData).then(
            (result) => {
                if (!result) {
                    setData(() => { return { id: '', pw: '', name: '' } });
                    console.log(userData);
                }
            }
        )
    };

    const handleKeyPress = (e) => {
        if (e.charCode == 13) {
            // enter pressed
            if (mode) {
                HandleLogin(e);
            } else {
                HandleRegister(e);
            }
        }
    };

    const myInputBox = (
        <div>
            <div className="input-field col s12 username">
                <label>UserId</label>
                <input
                    name="userId"
                    type="text"
                    className="validate"
                    onChange={onIdChange}
                    value={userData.id} />
            </div>
            <div className="input-field col s12">
                <label>Password</label>
                <input
                    name="password"
                    type="password"
                    className="validate"
                    onChange={onPwChange}
                    value={userData.pw}
                    onKeyPress={handleKeyPress} />
            </div>
        </div>
    );

    const loginView = (
        <div>
            <div className="card-content">
                <div className="row">
                    {myInputBox}
                    <button className="waves-effect waves-light btn" onClick={HandleLogin}>SUBMIT</button>
                </div>
            </div>

            <div className="footer">
                <div className="card-content">
                    <img src='kakao_login_medium_wide.png' alt='kakao login' width='95%' onClick={kakaoLogin} />
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
                <div className="input-field col s12">
                    <label>Nick Name</label>
                    <input
                        name="userName"
                        type="text"
                        className="validate"
                        onChange={onNameChange}
                        value={userData.name} />
                </div>
                {myInputBox}
                <button className="waves-effect waves-light btn" onClick={HandleRegister}>CREATE</button>
            </div>
        </div>
    );

    return (
        <div className="container auth">
            <Link className="logo" to="/">WhereAreYou</Link>
            <div className="card">
                <div className="header yellow darken-1 white-text center">
                    <div className="card-content">{mode ? "LOGIN" : "REGISTER"}</div>
                </div>
                {mode ? loginView : registerView}
            </div>
        </div>
    );
};

export default Authentication;
