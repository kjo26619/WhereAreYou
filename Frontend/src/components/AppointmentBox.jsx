import React, {useState } from 'react';
import "./AppointmentBox.scss";

function AppointmentBox(){
    return(
        <div className="AppointmentBox" >
            <table>
                <thead>
                    <tr>
                        <th>AppointmentBox</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>appointmentNo</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>name</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>placeName</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>placeX</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>placeY</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>openToAll</td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
};

export default AppointmentBox;