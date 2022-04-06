//import React, { useState } from 'react';
import axios from 'axios';

const baseURL = "http://localhost:8080";

export function getUser(){
    const url = baseURL + "/user";

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
            user_id: "userId",
            pw : "pw",
            userx : 1.1,
            usery : 2.2,
            kakao_id: 123,
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
    const url = baseURL + "/user";

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
