import React, {useState} from 'react';
import {Link} from 'react-router-dom'
import { PropTypes } from 'prop-types';
import Navbar from './Navbar';
import './Header.scss'
import MyModal from './MyModal';
import * as BsIcons from 'react-icons/bs';

Header.propTypes = {
    isLoggedIn: PropTypes.bool,
    onLogout: PropTypes.func
};

Header.defaultProps = {
    isLoggedIn: false,
    onLogout: () => { console.error("logout function not defined");}
};

function Header({isLoggedIn, onLogout}) {

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

    const onClick = ()=>{
        console.log("clicked!!", isLoggedIn)
    }

    const loginButton = (
        <li>
            <Link to="/login">
            <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,700,1,200" />
                <button className="btn-img">
                <div className='img-bg-no-border'>
                    <i class="material-symbols-outlined">key</i>
                </div>
                </button>
            </Link>
        </li>
    );

    const logoutButton = (
        <li>
            <a onClick={onLogout}>
                <i className="material-icons">lock_open</i>
            </a>
        </li>
    );

    return (
        <nav className='header'>
            <div className="header-wrapper">
                <div className="left">
                    <ul>
                        {/* <li>
                            <Link to="/write">
                                <i className="material-icons">search</i>
                            </Link>
                        </li> */}
                        
                        <li>
                            <Link to="/" className="logo-left">WAY</Link>
                        </li>
                        
                    </ul>
                </div>

                <div className="right">
                    <div className="list-component">
                        <Navbar/>
                    </div>
                    <div className="list-component">
                        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,700,1,200" />
                        <button className="btn-img" onClick={handleClick}>
                            <div className='img-bg-no-border'>
                            <i class="material-symbols-outlined">add</i>
                            </div>
                        </button>
                        <MyModal isOpen={isOpen} onSubmit={handleClickSumbit} onCancel={handleClickCancel} />
                    </div>
                    <div className="list-component" onClick={onClick}>
                        { isLoggedIn ? logoutButton : loginButton }
                    </div>
                </div>
            </div>
        </nav>
    )
};

export default Header;

/**
 * 
kakao login response 

connected_at: "2022-03-20T12:13:18Z"
id: 2168271479
kakao_account:
profile:
is_default_image: false
nickname: "?��?��"
profile_image_url: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_640x640.jpg"
thumbnail_image_url: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_110x110.jpg"
[[Prototype]]: Object
profile_image_needs_agreement: false
profile_nickname_needs_agreement: false
[[Prototype]]: Object
properties:
nickname: "?��?��"
profile_image: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_640x640.jpg"
thumbnail_image: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_110x110.jpg"


            // kakao logout
            if (!window.Kakao.Auth.getAccessToken()) {
                console.log('Not logged in.');
                return;
            }
            window.Kakao.Auth.logout(function() {
                console.log('logout');
                modifier(0);
            });
 */