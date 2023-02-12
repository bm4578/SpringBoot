import request from '../utils/request.js'
import qs from 'qs';

export function user_login(data){
    return request({
        url:'/api/user/login',
        method:"post",
        data:qs.stringify(data)
    })
}

export function user_info(data){
    return request({
        url:'/api/file',
        method:"get",
        data:qs.stringify(data)
    })
}