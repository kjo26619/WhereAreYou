import React from 'react';
import "./AddAppointment.scss";

function AddAppointment(){
    return(
        <div className="Add-Appointment" >
            <div class="input-box">
                <input type="text" name="" required>
                </input>
                <label>Appointment Name</label>
            </div>

            <div class="input-box">
                <input type="text" name="" required>
                </input>
                <label>Place</label>
            </div>

            <div class="input-box">
                <input type="date" name="" required="">
                </input>
                <label>Calender</label>
            </div>

            <div class="input-box">
                <input type="text" name="" required>
                </input>
                <label>Friends</label>
            </div>
        </div>
    );
};

export default AddAppointment;