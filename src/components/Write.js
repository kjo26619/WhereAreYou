import React from 'react';
import { PropTypes } from 'prop-types';

Write.propTypes = {
    onPost: PropTypes.func
};

Write.defaultProps = {
    onPost: (contents) => { console.error('post function not defined'); }
};

function Write({ onPost }) {
    const [MemoName, setMemoName] = React.useState("");
    const handleMemoName = (e) => {
        setMemoName(e.target.value);
        console.log('Appointment Name: ', { MemoName });
    }
    const [isOpen, setState] = React.useState(true);
    const toggleState = () => {
        setState(isOpen => !isOpen);
        console.log('toggled ! : ', { isOpen });
    }
    const handlePost = () => {
        let contents = { name: MemoName, open: isOpen };
        onPost(contents).then(
            () => {
                setMemoName("");
                setState(false);
            }
        )
    }
    return (
        <div className="container write">
            <div className="card">
                <div className="card-content">
                    <textarea
                        className="materialize-textarea"
                        placeholder="약속 이름을 입력하세요"
                        value={MemoName}
                        onChange={handleMemoName}></textarea>
                    <textarea className="materialize-textarea" placeholder="약속 장소를 입력하세요"></textarea>
                    <div className="toggleBox">
                        <div className={isOpen ? 'toggleBG' : 'toggleBG_On'} onClick={toggleState}>
                            <button id='buttonID' className={isOpen ? 'toggleFG' : 'toggleFG_On'}></button>
                        </div>
                        <div className='explain'>공개 할까요? : {isOpen ? '아니요' : '예'}</div>
                    </div>
                </div>
                <div className="card-action">
                    <a onClick={handlePost}>POST</a>
                </div>
            </div>
        </div>
    );
};

export default Write;