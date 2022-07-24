import React from 'react';
import {useLocation} from 'react-router';

function addUser(code){

    return true;
}

function InvitedPage(){
    const location = useLocation();
    let code = '';
    if(location.state?.from){
        code = String(location.state.from.pathname).split("/")[2];
        addUser(code);
    }

    return (
        <div>
            <h2>{code}</h2>
        </div>
    );
}


export default InvitedPage;