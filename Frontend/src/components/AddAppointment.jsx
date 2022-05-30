import React from 'react';
import AppointmentBox from './AppointmentBox';
import {AppointmentApi, addAppointment, getAppointment} from "../axios/AppointmentApi";

function AddAppointment(){
    
    const handleClickAdd = () => {
        addAppointment();
    }
    
    const handleClickGet = () => {
        getAppointment();
    }

    return(
        <div className="tmp" >
            <button onClick={handleClickAdd}>추가 테스트</button>
            <button onClick={handleClickGet}>no 1 가져오기 테스트</button>
            <AppointmentBox />
        </div>
    );
};

export default AddAppointment;