import React, { useState, useEffect } from "react";
import ReactModal from 'react-modal';
import AppointmentList from "./AppointmentList";

function MyModal({isOpen, onSubmit, onCancel }){
    const handleClickSubmit = () => {
        onSubmit();
    }

    const handleClickCancel = () => {
        onCancel();
    }

    return (
        <ReactModal 
            isOpen={isOpen}
            ariaHideApp={false}
            >
            <div>
                <AppointmentList/>
            </div>
            <div>
                <button onClick={handleClickSubmit}>확인</button>
                <button onClick={handleClickCancel}>취소</button>
            </div>
        </ReactModal>
    );
}

export default MyModal;