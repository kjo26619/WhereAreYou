import axios from 'axios';
import * as API from './API';

const baseURL = "/api";

export function getAppointment(data, setData){
    const url = baseURL + "/appointment?no=1";

    axios.get(url)
    .then((res) => {
        /*setData(data => [...data, {
            appoinmentNo: "약속 번호2", 
            name: "약속 이름", 
            placeName: "약속 장소",
            placeX: 1.1,
            placeY: 2.2,
        }]);*/
        console.log(res.data);
        setData(data => [...data, res.data]);
    })
    .catch((error) => {
        console.log(error);
    });
}

export function addAppointment(){
    const url = baseURL + "/appointment";

    axios.post(url, {
            appointmentNo: 1,
            name: "appointment name",
            placeName : "placeName",
            placeX : 1.1,
            placeY : 2.2,
            openToAll: false,
    },
    {
        Credential : true,
    })
    .then((res) => {
        console.log(res);
    })
    .catch((error) => {
        console.log(error);
    })
}


export function AppointmentApi() {

}