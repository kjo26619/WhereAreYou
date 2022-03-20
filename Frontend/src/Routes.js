import React from 'react';
import { BrowserRouter as Router, Route, Routes as Switch, Link } from 'react-router-dom';

import Header from './components/Header';
import Home from './containers/Home';
import Login from './containers/Login';

function Routes(){
    return (
        <Router>
            <Header/>
            <Switch>
                <Route exact path='/login' element={<Login/>}/>
                <Route exact path='/' element={<Home/>}/>
            </Switch>
        </Router>
    );
}

export default Routes;