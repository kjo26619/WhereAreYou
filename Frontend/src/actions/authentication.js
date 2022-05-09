import axios from 'axios';
import * as types from './ActionTypes';

const baseURL = "http://localhost:8080";

/* LOGIN */
export function loginRequest(username, password) {
    const url = baseURL + '/join';
    console.log('[login request] url :', url, 'id : ', username, 'pw : ', password);
    return (dispatch) => {
        // Inform Login API is starting
        dispatch(login());

        // API REQUEST
        return axios.post(url, { username, password }, {Credential : true})
            .then((response) => {
                // SUCCEED
                dispatch(loginSuccess(username));
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
export function registerRequest(username, password) {
    const url = baseURL + '/join';
    console.log('[login request] url :', url, 'id : ', username, 'pw : ', password);

    return (dispatch) => {
        // Inform Register API is starting
        dispatch(register());

        return axios.post(url, { username, password }, { Credential : true})
        .then((response) => {
            dispatch(registerSuccess());
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
export function logoutRequest() {
    return (dispatch) => {
        return axios.post(baseURL)
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