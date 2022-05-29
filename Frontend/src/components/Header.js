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
                <i className="material-icons">vpn_key</i>
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
                <Link to="/" className="brand-logo center">WAY</Link>
                <ul>
                    {/* <li>
                        <Link to="/write">
                            <i className="material-icons">search</i>
                        </Link>
                    </li> */}
                    <li>
                        <Navbar/>
                    </li>
                    <li>
                        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,600,0,200" />
                        <button className="btn-img" onClick={handleClick}>
                            <div className='img-bg-no-border'>
                            <i class="material-symbols-outlined">add</i>
                            </div>
                        </button>
                        <MyModal isOpen={isOpen} onSubmit={handleClickSumbit} onCancel={handleClickCancel} />
                    </li>
                </ul>

                <div className="right">
                    <ul onClick={onClick}>
                        { isLoggedIn ? logoutButton : loginButton }
                    </ul>
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