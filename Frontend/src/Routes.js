import React from 'react';
import { BrowserRouter as Router, Route, Routes as Switch } from 'react-router-dom';

import { Header, Write } from './components';
import { Home, Login, Register } from './containers';

function Routes(){
    return (
        <Router>
            <Header/>
            <Switch>
                <Route exact path='/login' element={<Login/>}/>
                <Route exact path='/' element={<Home/>}/>
                <Route exact path='/write' element={<Write/>}/>
                <Route exact path='/Register' element={<Register/>}/>
            </Switch>
        </Router>
    );
}

export default Routes;