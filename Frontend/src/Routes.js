 import React from 'react';
import { BrowserRouter as Router, Route, Routes as Switch } from 'react-router-dom';
import { Write, InviteLink, InvitedPage } from './components';
import { App, Home, Login, Register, Test, BlockTest } from './containers';

function Routes() {
    return (
        <Router>
            <App />
            <Switch>
                <Route path='/' element={<Home />} />
                <Route path='/login' element={<Login />} />
                <Route path='/write' element={<Write />} />
                <Route path='/Register' element={<Register />} />
                <Route path='/Test' element={<Test />} />
                <Route path='/BlockTest' element={<BlockTest />} />
                <Route path='/InviteLink/:code' element={<InviteLink/>} />
                <Route path='/InvitedPage' element={<InvitedPage/>} />
            </Switch>
        </Router>
    );
}

export default Routes;