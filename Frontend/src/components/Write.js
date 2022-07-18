import React from 'react';
import { PropTypes } from 'prop-types';
import { SearchPlace } from '../components'

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
    const handlePost = () => {
        let contents = {
            "name": MemoName,
            "placeName": "ARA'S HOUSE",
            "placeX": 2.2,
            "placeY" : 3.3
        }
        onPost(contents).then(
            () => {
                setMemoName("");
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
                    
                    <SearchPlace />
                </div>
                <div className="card-action">
                    <a onClick={handlePost}>POST</a>
                </div>
            </div>
        </div>
    );
};

export default Write;