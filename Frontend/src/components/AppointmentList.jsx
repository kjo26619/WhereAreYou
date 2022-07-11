import React from 'react';
import "./AppointmentList.scss";
import ListView from './ListView';

function AppointmentList(){
    return(
        <div className="AppointmentList" >
            <ListView/>
        </div>
    );
};

export default AppointmentList;