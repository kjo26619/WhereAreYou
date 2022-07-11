import React from 'react';
import {PropTypes} from 'prop-types';

Write.propTypes = {
    onPost: PropTypes.func
};

Write.defaultProps = {
    onPost: (contents) => { console.error('post function not defined'); }
};

function Write(){
    const [isOpen, SetState] = React.useState(true);
    const toggleState = ()=>{
        SetState(isOpen=>!isOpen);
        console.log('toggled ! : ' , {isOpen});
    }
    return (
        <div className="container write">
            <div className="card">
                <div className="card-content">
                    <textarea className="materialize-textarea" placeholder="약속 이름을 입력하세요"></textarea>
                    <textarea className="materialize-textarea" placeholder="약속 장소를 입력하세요"></textarea>
                    <div className="toggleBox">
                        <div className= {isOpen?'toggleBG':'toggleBG_On'} onClick={toggleState}>
                            <button id='buttonID' className={isOpen ?'toggleFG':'toggleFG_On'}></button>
                        </div>
                        <div className='explain'>공개 할까요? : {isOpen ? '아니요' : '예'}</div>
                    </div>
                </div>
                <div className="card-action">
                    <button>POST</button>
                </div>
            </div>
        </div>
    );
};

export default Write;