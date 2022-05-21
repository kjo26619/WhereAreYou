import React, { useState, useEffect } from "react";
import ReactModal from 'react-modal';
import AppointmentList from "./AppointmentList";
import './MyModal.scss';

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
            className='modal-wrapper'
            >
            <div className = 'modal-content'>
                <header className = 'modal-header'>
                    <h3> TEST MODAL </h3>
                </header>

                <div className = 'modal-bg'>
                    <AppointmentList/>
                </div>
                
                <footer className='modal-footer'>
                    <button className='modal-btn-1' onClick={handleClickSubmit}>확인</button>
                    <button className='modal-btn-2' onClick={handleClickCancel}>취소</button>
                </footer>
            </div>
        </ReactModal>
    );
}

export default MyModal;