import React from 'react';
import {Appointment} from '../components'
import { PropTypes } from 'prop-types';

function AppointmentList({data, currentUser}){
    const mapToComponents = data =>{
        return data.map((appointment, i) =>{
            return (
            <Appointment data={appointment} 
                key={appointment.appointmentNo}
            />);
        });
    };

    return(
        <div className="AppointmentList" >
            {mapToComponents(data)}
        </div>
    );
};

AppointmentList.propTypes = {
    data:PropTypes.array,
    currentUser: PropTypes.string
};

AppointmentList.defaultProps = {
    data: [],
    currentUser: ''
};

export default AppointmentList;