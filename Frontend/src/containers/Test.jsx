import React, { useState } from "react";
import MyModal from '../components/MyModal';
import AddAppointment from "../components/AddAppointment";
import axios from 'axios';

axios.defaults.headers.common['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

function Test() {
    const [isOpen, setOpen] = useState(false);

    const handleClick = () => {
        setOpen(true);
    }

    const handleClickSumbit = () => {
        setOpen(false);
    }

    const handleClickCancel = () => {
        setOpen(false);
    }

    const testClick = () => {
        const no = document.getElementById("NO").value;
        console.log(no);
        const url = "http://localhost:8081/api/getUserList";

        axios.get(url, {
            params: {
                no: no
            }
        })
        .then((res) => {
            console.log(res);
        })
        .catch((error) => {
            console.log(error);
        })
    }

    return (
        <div>
            <button onClick={handleClick}> Modal Open </button>
            <AddAppointment />
            <MyModal isOpen={isOpen} onSubmit={handleClickSumbit} onCancel={handleClickCancel} />

            <div>
            <input type="text" id="NO"></input>
            <button onClick={testClick}> Test </button>
            </div>
        </div>
    );
}

export default Test;