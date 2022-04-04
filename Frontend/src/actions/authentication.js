import axios from 'axios';
import * as types from './ActionTypes';

/* LOGIN */
export function loginRequest(username, password) {
    return (dispatch) => {
        // Inform Login API is starting
        dispatch(login());

        // API REQUEST
        return axios.post('/api/account/signin', { username, password })
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
