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
                    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,600,0,200" />
                    <button className='modal-btn-img' onClick={handleClickSubmit}>
                        <div className='img-bg'>
                        <i class="material-symbols-outlined">done</i>
                        </div>
                        <em>확인</em>
                        
                    </button>
                    <button className='modal-btn-img' onClick={handleClickCancel}>
                        <div className='img-bg'>
                        <i class="material-symbols-outlined">close</i>
                        </div>
                        <em>취소</em>
                    </button>
                </footer>
            </div>
        </ReactModal>
    );
}

export default MyModal;