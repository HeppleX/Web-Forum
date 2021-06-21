import request from '../utils/request';
import qs from 'qs'


/* 统一封装get请求 */
export const get = (url,params) => {
    return request({
		url:url,
        method: 'get',
        params:params
    })
}

/* 统一封装post请求  */
export const post = (url, data) => {
    return request({
		url:url,
        method: 'post',
        data: qs.stringify(data)
    })
}