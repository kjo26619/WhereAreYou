import React from "react";
import ReactModal from 'react-modal';
import Map from "./MainMap";
import SearchPlace from "./SearchPlace";

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
                <button onClick={handleClickSubmit}>확인</button>
                <button onClick={handleClickCancel}>취소</button>
            </div>
            <div>
                <Map/>
                <SearchPlace/>
            </div>
        </ReactModal>
    );
}

export default MyModal;