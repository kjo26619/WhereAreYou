import axios from 'axios';

const baseURL = "http://localhost:8080";

export function getAppointment(){
    const url = baseURL + "/appointment/1";

    axios.get(url)
    .then((res) => {
        console.log(res);
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