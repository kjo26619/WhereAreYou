import React from 'react';

function Header() {
    const [isLoggedIn, modifier] = React.useState(0);
    const onClick = (e)=>{
        console.log("clicked!!")
        modifier(1);
        console.log(e.target);
    }
    const loginButton = (
        <li>
        <a href="() => false">
                <i className="material-icons">vpn_key</i>
            </a>
        </li>
    );

    const logoutButton = (
        <li>
            <a href="() => false">
                <i className="material-icons">lock_open</i>
            </a>
        </li>
    );

    return (
        <nav>
            <div className="nav-wrapper yellow darken-1">
                <a href="() => false" className="brand-logo center">WAY</a>
                <ul>
                    <li>
                        <a href="() => false">
                            <i className="material-icons">search</i>
                        </a>
                    </li>
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