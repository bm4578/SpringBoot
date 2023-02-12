import axios from 'axios';
import {userStore} from "../store/user/user";
//1. 创建axios对象
const service = axios.create();

//2. 请求拦截器
service.interceptors.request.use(config => {
    const store = userStore();
    let userToken = store.token;
    if (userToken){
        config.headers['token'] = userToken;
    }
    return config;
}, error => {
    Promise.reject(error);
});

//3. 响应拦截器
service.interceptors.response.use(response => {
    //判断code码
    return response.data;
},error => {
    return Promise.reject(error);
});

export default service;