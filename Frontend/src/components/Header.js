import React from 'react';
import {Link} from 'react-router-dom'

function Header() {
    const [isLoggedIn, modifier] = React.useState(0);

    const onClick = ()=>{
        console.log("clicked!!", isLoggedIn)

        if(isLoggedIn)
        {
            // kakao logout
            if (!window.Kakao.Auth.getAccessToken()) {
                console.log('Not logged in.');
                return;
            }
            window.Kakao.Auth.logout(function() {
                console.log('logout');
                modifier(0);
            });
        }
        else
        {
            // kakao login
            if(!window.Kakao.isInitialized())
            {
                window.Kakao.init('283559fa033c8cf5d3a8289adcc25596');
                console.log('kakao init: ', window.Kakao.isInitialized());
            }
            window.Kakao.Auth.login({
                success: function (response) {
                  window.Kakao.API.request({
                    url: '/v2/user/me',
                    success: function (response) {
                        console.log("success: ", response);
                        modifier(1);
                    },
                    fail: function (error) {
                      console.log(error)
                    },
                  })
                },
                fail: function (error) {
                  console.log(error)
                },
            })
        }
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
            <a>
                <i className="material-icons">lock_open</i>
            </a>
        </li>
    );

    return (
        <nav>
            <div className="nav-wrapper yellow darken-1">
                <Link to="/" className="brand-logo center">WAY</Link>
                <ul>
                    <li>
                        <Link to="/write">
                            <i className="material-icons">search</i>
                        </Link>
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
nickname: "현정"
profile_image_url: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_640x640.jpg"
thumbnail_image_url: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_110x110.jpg"
[[Prototype]]: Object
profile_image_needs_agreement: false
profile_nickname_needs_agreement: false
[[Prototype]]: Object
properties:
nickname: "현정"
profile_image: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_640x640.jpg"
thumbnail_image: "http://k.kakaocdn.net/dn/b4qafq/btrkkX4WXfg/zHA8LzUXaZtRslgtTbjyXK/img_110x110.jpg"

 */