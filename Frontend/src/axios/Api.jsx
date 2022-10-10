import axios from "axios";

export const BASE_URL = "http://ec2-3-36-123-40.ap-northeast-2.compute.amazonaws.com:49159/api";
export const LOGIN_API = "/login"

export function post (url, data, header){
    return axios.post(BASE_URL + url, data, header);
}