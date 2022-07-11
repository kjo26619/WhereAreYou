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
    onRegister: (id, pw) => { console.log("register function not defined") }
};

function Authentication({ mode , onLogin, onRegister}) {
    const [userName, setName] = useState("");
    const [userPW, setPw] = useState("");

    const onNameChange = (e)=>{
        setName(e.target.value);
        console.log(userName);
    };

    const onPwChange = (e)=>{
        setPw(e.target.value);
        console.log(userPW);
    }

    const kakaoLogin = (e)=> {
        // kakao login
        if(!window.Kakao.isInitialized())
        {
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

    const HandleLogin = (e)=>{
        onLogin(userName, userPW).then(
            (success)=>{
                if(!success){
                    setPw('');
                }
            }
        )
    };

    const HandleRegister = (e)=>{
        onRegister(userName, userPW).then(
            (result)=>{
                if(!result){
                    setName('');
                    setPw('');
                }
            }
        )
    };

    const handleKeyPress = (e)=>{
        if(e.charCode===13) {
            console.log('enter pressed');
            if(mode) {
                HandleLogin(e);
            } else {
                HandleRegister(e);
            }
        }
    };

    const myInputBox = (
        <div>
            <div className="input-field col s12 username">
                <label>Username</label>
                <input
                    name="username"
                    type="text"
                    className="validate"
                    onChange={onNameChange}
                    value={userName}/>
            </div>
            <div className="input-field col s12">
                <label>Password</label>
                <input
                    name="password"
                    type="password"
                    className="validate" 
                    onChange={onPwChange}
                    value={userPW}
                    onKeyPress={handleKeyPress}/>
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
                    <img src='kakao_login_medium_wide.png' alt='kakao login' width='95%' onClick={kakaoLogin}/>
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
