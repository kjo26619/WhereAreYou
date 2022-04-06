import React from 'react';
import {Authentication} from '../components';
import * as LoginApi from '../axios/LoginApi';

function Login(){
    return (
        <div>
            <Authentication />
            <button onClick={LoginApi.getUser}> Login Test </button>
            <button onClick={LoginApi.isExistUser}> existsTest </button>
            <button onClick={LoginApi.joinUser}> Join Test </button>
            <button onClick={LoginApi.deleteUser}> Delete Test </button>
            <button onClick={LoginApi.updateUser}> Update Test </button>
        </div>
    );
}

export default Login;