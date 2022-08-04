import {
    MEMO_POST,
    MEMO_POST_SUCCESS,
    MEMO_POST_FAILURE,
    MEMO_LIST,
    MEMO_LIST_SUCCESS,
    MEMO_LIST_FAILURE
} from './ActionTypes';
import axios from 'axios';

const baseURL = "http://localhost:8081/api";

/* MEMO POST */
export function memoPostRequest(contents) {
    const url = baseURL + '/appointment'
    const param = {
        userId: 'hyun'
    }
    console.log('[memo request] url :', url, 'Content:', contents, 'param:', param);
    return (dispatch) => {
       // inform MEMO POST API is starting
        dispatch(memoPost());
        return axios.post(url, contents, {params: param}, {credential:true})
        .then((response) => {
            dispatch(memoPostSuccess());
        }).catch((error) => {
            dispatch(memoPostFailure(error.response.data.code));
        });
    };
}

export function memoPost() {
    return {
        type: MEMO_POST
    };
}

export function memoPostSuccess() {
    return {
        type: MEMO_POST_SUCCESS
    };
}

export function memoPostFailure(error) {
    return {
        type: MEMO_POST_FAILURE,
        error
    };
}

/* MEMO LIST */

/*
    Parameter:
        - isInitial: whether it is for initial loading
        - listType:  OPTIONAL; loading 'old' memo or 'new' memo
        - id:        OPTIONAL; memo id (one at the bottom or one at the top)
        - username:  OPTIONAL; find memos of following user
*/
export function memoListRequest(isInitial, listType, id, username) {
    return (dispatch) => {
        // inform memo list API is starting
        dispatch(memoList());
        
        let url = baseURL + "/appointment?no=1";
        
        /* url setup depending on parameters,
           to  be implemented.. */
          
        return axios.get(url)
        .then((response) => {
            dispatch(memoListSuccess(response.data, isInitial, listType));
            console.log(response.data);
        }).catch((error) => {
            dispatch(memoListFailure());
        });
    };
}

export function memoList() {
    return {
        type: MEMO_LIST
    };
}

export function memoListSuccess(data, isInitial, listType) {
    return {
        type: MEMO_LIST_SUCCESS,
        data,
        isInitial,
        listType
    };
}

export function memoListFailure() {
    return {
        type: MEMO_LIST_FAILURE
    };
}