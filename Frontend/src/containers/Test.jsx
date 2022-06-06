import React, { useState } from "react";
import MyModal from '../components/MyModal';
import AddAppointment from "../components/AddAppointment";


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

    return (
        <div>
            <button onClick={handleClick}> Modal Open </button>
            <AddAppointment />
            <MyModal isOpen={isOpen} onSubmit={handleClickSumbit} onCancel={handleClickCancel} />
        </div>
    );
}

export default Test;