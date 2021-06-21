import axios from 'axios';
import { Loading } from 'element-ui'
import router from '../router/index'
import vuex from '../store/index.js'
import qs from 'qs'
let loadingInstance = null
const service = axios.create({

    baseURL: 'http://127.0.0.1:8098',
    timeout: 5000,

    // // 在向服务器发送请求前，序列化请求数据
    // transformRequest: [function (data) {
    //     data = qs.stringify(data)
    //     return data
    // }],

    // 在传递给 then/catch 前，修改响应数据 
    transformResponse: [function (data) {
        if (typeof data === 'string' && data.startsWith('{')) {
            data = JSON.parse(data)
        }
        return data
    }],


    headers: {
        get: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
            // 在开发中，一般还需要单点登录或者其他功能的通用请求头，可以一并配置进来
        },
        post: {
            // 'Content-Type': 'application/json;charset=utf-8',
            // 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
            // 在开发中，一般还需要单点登录或者其他功能的通用请求头，可以一并配置进来
        }
    }

});
service.defaults.withCredentials = true
// 请求拦截器
service.interceptors.request.use(
    config => {
        // 写带token
        config.headers.token = vuex.state.token

        loadingInstance = Loading.service({
            lock: true,
            text: 'loading...'
        })
        return config;
    },
    error => {
        loadingInstance.close()
        console.log(error);
        return Promise.reject();
    }
);

// 响应拦截器
service.interceptors.response.use(
    response => {
        loadingInstance.close()
        if (response.data.code == 1008) {
            console.log(response.data.msg);
            vuex.commit('setToken', "");
            router.push({
                path: "/"
            })
            // this.$message('请登陆!')
        }

        if (response.status === 200) {
            return response.data;
        } else {
            Promise.reject();
        }
    },
    error => {
        loadingInstance.close()
        //      if(error.request.status == 403){
        //          console.log("未登录")
        //          router.push({
        // 	path: "/"
        // })
        //      }else{
        //          this.$message.error("服务器错误");
        //      }
        return Promise.reject();
    }
);

export default service;
