import React from 'react';
import {useLocation} from 'react-router';
import {Navigate} from 'react-router-dom';

function InviteLink(){
    const location = useLocation();
    return (
        <div>
            <Navigate to="/InvitedPage" replace state={{from : location}}/>
        </div>
    );
}


export default InviteLink;