import React from 'react';

function Header() {
    const [isLoggedIn, modifier] = React.useState(0);
    const onClick = ()=>{
        console.log("clicked!!")
        modifier(1);
    }
    const loginButton = (
        <li>
            <a>
                <i className="material-icons">vpn_key</i>
            </a>
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
                <a className="brand-logo center">WAY</a>
                <ul>
                    <li><a><i className="material-icons">search</i></a></li>
                </ul>

                <div className="right">
                    <ul onClick={onClick}>
                        { isLoggedIn ? loginButton : logoutButton}
                    </ul>
                </div>
            </div>
        </nav>
    )
};

export default Header;