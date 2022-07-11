import React, {useState} from 'react';
import {getAppointment} from "../axios/AppointmentApi";

function AddAppointment(){

    const [data, setData] = useState([{appointmentNo: "약속 번호", 
                                        name: "약속 이름", 
                                        placeName: "약속 장소",
                                        placeX: 1.1,
                                        placeY: 2.2,
                                    }]);

    // const handleClickAdd = () => {
    //     addAppointment();
    // }
    
    const handleClickGet = () => {
        getAppointment(data, setData);
    }

    return(
        <div className="tmp" >
            {/* <button onClick={handleClickAdd}>추가 테스트</button>
            <button onClick={handleClickGet}>no 1 가져오기 테스트</button>
            <AppointmentBox /> */}
            <button onClick={handleClickGet}>no = 1 appointment 가져오기 테스트</button>
            <table>
                {data.map(({appointmentNo, name, placeName, placeX, placeY})=>(
                    <tbody key={appointmentNo}>
                        <tr>
                            <td>appointmentNo</td>
                            <td><label datatype='number'>{appointmentNo}</label></td>
                        </tr>
                    <tr>
                        <td>name</td>
                        <td><input datatype='text' placeholder={name}></input></td>
                    </tr>
                    <tr>
                        <td>placeName</td>
                        <td><input placeholder={placeName}></input><button>장소 검색</button></td>
                    </tr>
                    <tr>
                        <td>placeX</td>
                        <td><label>{placeX}</label></td>
                    </tr>
                    <tr>
                        <td>placeY</td>
                        <td><label>{placeY}</label></td>
                    </tr>
                    <tr>
                        <td><button> OPEN TO ALL</button></td>
                    </tr>
                    </tbody>
                ))}
            </table>
        </div>
    );
};

export default AddAppointment;