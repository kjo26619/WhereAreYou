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
    const [placeData, setPlaceData] = React.useState({
        "placeName": '',
        "placeX": '',
        "placeY": ''
    });
    const handleMemoName = (e) => {
        setMemoName(e.target.value);
        console.log('Appointment Name: ', { MemoName });
    };
    const handlePost = () => {
        if(MemoName==='') {
            console.log('appointment Name is null');
            return false;
        }
        if(placeData.placeName==='' || placeData.x==='' || placeData.y===''){
            console.log('one of placeData is null');
            return false;
        }
        let contents = {
            "name": MemoName,
            "placeName": placeData.placeName,
            "placeX": placeData.placeX,
            "placeY": placeData.placeY
        }
        onPost(contents).then(
            () => {
                setMemoName("");
                setPlaceData({
                    "placeName": '',
                    "placeX": '',
                    "placeY": ''
                });
            }
        )
    }
    const setPlace = (place) => {
        console.log('place Data updated: ', place);
        setPlaceData((prevState) => {
            return {
                ...prevState,
                placeName: place.placeName,
                placeX: place.placeX,
                placeY: place.placeY
            }
        });
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

                    <SearchPlace onPost={setPlace} />
                </div>
                <div className="card-action">
                    <a onClick={handlePost}>POST</a>
                </div>
            </div>
        </div>
    );
};

export default Write;