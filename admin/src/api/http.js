import {get,post} from './index'

// 管理员登陆
export  const sysLogin = function(data){
	return post("/sys/login",data)
}
// 获取所有的圈子列表
export const circleAll = function(){
	return get("/sys/circle/list");
}

// 获取所有的用户信息
export const userAll = function () {
	return get("/sys/user/list")
}
// 获取所有的留言
export const messageAll = function () {
	return get('/sys/message/list')
}

// 获取所有的文章
export  const articleAll = function () {
	return get('/sys/article/list')
}
// 根据留言主键删除
export const deleteMessage = function (data) {
	return post("/sys/delete/message",data)
}
// 圈子添加
export  const addCircle = function (data) {
	return post("/sys/add/circle",data)
}
// 圈子更新
export  const updateCircle = function (data) {
	return post("/sys/update/circle",data)
}
// 用户分值添加
export const addUserScopes = function (data) {
	return post('/sys/add/user/scope',data)
}
// 删除用户
export const removeUser = function (data) {
	return post("/sys/delete/user",data)
}