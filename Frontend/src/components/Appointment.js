import React, { useEffect, useRef } from 'react';
import { PropTypes } from 'prop-types';
import TimeAgo from 'react-timeago';

Appointment.propTypes = {
    data: PropTypes.object,
    owenership: PropTypes.bool
}

Appointment.defaultProps = {
    data: {
        _id: 'id123456',
        writer: 'testWriter',
        contents: {
            name: 'newAppointmentName',
            open: true
        },
        id_edited: false,
        date: {
            edited: new Date(),
            created: new Date()
        }
    },
    owenership: true
}

function Appointment({ data, owenership }) {
    const mounted = useRef(false);
    useEffect(() =>{
        // WHEN COMPONENT MOUNTS, INITIALIZE DROPDOWN
        if(!mounted.current){
            mounted.current = true;
        }else{
            // (TRIGGERED WHEN REFRESHED)
        }
    }, []);
    const dropDownMenu = (
        <div className="option-button">
            <a className='dropdown-button'
                id={`dropdown-button-${data._id}`}
                data-activates={`dropdown-${data._id}`}>
                <i className="material-icons icon-button">more_vert</i>
            </a>
            <ul id={`dropdown-${data._id}`} className='dropdown-content'>
                <li><a>Edit</a></li>
                <li><a>Remove</a></li>
            </ul>
        </div>
    );
    const memoView = (
        <div className="card">
            <div className="info">
                <a className="username">{data.name}</a> wrote a log · <TimeAgo date={data.time} />
                {/* {owenership ? dropDownMenu : undefined} */}
                <button>선택</button>
            </div>
            <div className="card-content">
                <div>{data.placeName}</div>
            </div>
            <div className="footer">
                <i className="material-icons log-footer-icon star icon-button">star</i>
                <span className="star-count">0</span>
            </div>
        </div>
    );
    return (
        <div className="container memo">
            {memoView}
        </div>
    )
};

export default Appointment;