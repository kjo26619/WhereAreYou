 import React from 'react';
import { BrowserRouter as Router, Route, Routes as Switch } from 'react-router-dom';
import { App, Home, Login, Register, Test, BlockTest, WriteAppointment } from './containers';

function Routes() {
    return (
        <Router>
            <App />
            <Switch>
                <Route path='/' element={<Home />} />
                <Route path='/login' element={<Login />} />
                <Route path='/write' element={<WriteAppointment />} />
                <Route path='/Register' element={<Register />} />
                <Route path='/Test' element={<Test />} />
                <Route path='/BlockTest' element={<BlockTest />} />
            </Switch>
        </Router>
    );
}

export default Routes;