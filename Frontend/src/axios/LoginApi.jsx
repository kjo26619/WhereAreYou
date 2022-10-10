//import React, { useState } from 'react';
import axios from 'axios';
import API from './API';

const baseURL = API.BASE_URL + "/api/user";

export function getUser(){
    const url = baseURL + "/exists";

    axios.get(url)
    .then((res) => {
        console.log(res);
    })
    .catch((error) => {
        console.log(error);
    });
}

export function joinUser(){
    const url = baseURL + "/join";

    axios.post(url, {
            name: "name",
            userId: "userId",
            pw : "pw",
            userX : 1.1,
            userY : 2.2,
            kakaoId: 123,
    },
    {
        Credential : true,
    })
    .then((res) => {
        console.log(res);
    })
    .catch((error) => {
        console.log(error);
    })
}

export function isExistUser(){
    let id = 1;
    const url = baseURL + "/user/" + id + "/exists";

    axios.get(url)
    .then( (res) => {
        console.log(res);
    })
    .catch((error) => {
        console.log(error);
    })
}

export function updateUser(){
    const url = baseURL + "/user/1";

    axios.put(url, {
            name: "name",
            userId: "userId",
            pw : "pw",
            userX : 1.1,
            userY : 2.2,
            kakaoId: 456,
    })
    .then((res) => {
        console.log(res);
    })
    .catch((error) => {
        console.log(error);
    })
}
export function deleteUser(){
    const url = baseURL + "/user";

    
}

export function LoginApi() {
    


}
