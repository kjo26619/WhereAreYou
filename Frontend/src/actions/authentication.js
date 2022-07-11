import axios from 'axios';
import * as types from './ActionTypes';

const baseURL = "http://localhost:8081/api";

/* LOGIN */
export function loginRequest(userId, password) {
    const url = baseURL + '/auth';
    console.log('[login request] url :', url, 'id : ', userId, 'pw : ', password);
    const userData = {
        userId: userId,
        pw: password
    }
    return (dispatch) => {
        // Inform Login API is starting
        dispatch(login());

        // API REQUEST
        return axios.post(url, null, { params : userData }, { Credential: true })
            .then((response) => {
                // SUCCEED
                dispatch(loginSuccess(userId));
                console.log(response.data);
            }).catch((error) => {
                // FAILED
                dispatch(loginFailure());
            });
    };
}

export function login() {
    return {
        type: types.AUTH_LOGIN
    };
}

export function loginSuccess(username) {
    return {
        type: types.AUTH_LOGIN_SUCCESS,
        username
    };
}

export function loginFailure() {
    return {
        type: types.AUTH_LOGIN_FAILURE
    };
}

/* REGISTER */
export function registerRequest(userData) {
    const url = baseURL + '/join';
    console.log('[register request] url :', url, 'name :', userData.name, 'id : ', userData.id, 'pw : ', userData.pw);
    const mUserData = {
        userId : userData.id,
        name : userData.name,
        password: userData.pw
    }
    return (dispatch) => {
        // Inform Register API is starting
        dispatch(register());

        return axios.post(url, null, {params:mUserData}).then((response) => {
            dispatch(registerSuccess());
            console.log(response);
        }).catch((error) => {
            dispatch(registerFailure(error.response.data.code));
        });
    };
}

export function register() {
    return {
        type: types.AUTH_REGISTER
    };
}

export function registerSuccess() {
    return {
        type: types.AUTH_REGISTER_SUCCESS,
    };
}

export function registerFailure(error) {
    return {
        type: types.AUTH_REGISTER_FAILURE,
        error
    };
}

/* GET STATUS */
export function getStatusRequest() {
    return (dispatch) => {
        // inform Get Status API is starting
        dispatch(getStatus());

        const url = baseURL + '/user'
        return axios.get(url)
            .then((response) => {
                dispatch(getStatusSuccess(response.data.info.username));
                console.log(response.data.info.username);
            }).catch((error) => {
                dispatch(getStatusFailure());
            });
    };
}

export function getStatus() {
    return {
        type: types.AUTH_GET_STATUS
    };
}

export function getStatusSuccess(username) {
    return {
        type: types.AUTH_GET_STATUS_SUCCESS,
        username
    };
}

export function getStatusFailure() {
    return {
        type: types.AUTH_GET_STATUS_FAILURE
    };
}

/* Logout */
export function logoutRequest(username, refreshToken) {
    const url = baseURL + '/logout';
    console.log('[logout request] url :', url, 'name :', username, 'refreshToken : ', refreshToken);
    return (dispatch) => {
        return axios.post(url, { refreshToken }, { Credential: true })
            .then((response) => {
                dispatch(logout());
            });
    };
}

export function logout() {
    return {
        type: types.AUTH_LOGOUT
    };
}