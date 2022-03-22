import React from 'react';

function Authentication(){
    const kakaoLogin = ()=>{
    }
    return(
        <div className="container auth">
            <div className="card center">
                <img src='kakao_login_medium_wide.png' alt='kakao login button' onClick={kakaoLogin}/>
            </div>
        </div>
    );
};

export default Authentication;