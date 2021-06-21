import { get, post } from './index'

// 用户登陆
export const api_Login = function (data) {
	return post("/api/login", data)
}
// 用户注册
export const api_Reg = function (data) {
	return post("/api/reg", data)
}
// 获取版块列表,根据分值排序
export const api_Open_Block = function () {
	return get("/open/block")
}
// 获取作者榜单
export const api_Open_Author_There = function () {
	return get("/open/select/user/score")
}
// 获取前三的版块
export const api_Open_Block_There = function () {
	return get("/open/select/block/score")
}
// 添加文章
export const api_save_article = function (data) {
	return post("/api/save/article", data)
}
// 分页查询文章，按时间排序
export const api_Open_article_new = function (data) {
	return post("/open/article/new", data)
}
// 分页查询文章，按热度和时间排序
export const api_Open_article_hot = function (data) {
	return post("/open/article/hot", data)
}
// 根据关注列表查询,没有分页
export const api_Open_article_follow = function (data) {
	return post("/open/article/follow", data)
}
// 根据文章主键获取文章内容
export const api_Open_article_id = function (id) {
	return get("/open/article/" + id)
}
// 判断是否点赞/收藏/关注
export const api_Open_enter = function (id) {
	return get("/open/get/enter/" + id)
}
// 文章点赞/取消操作
export const api_set_like = function (data) {
	return post("/api/add/like", data)
}
// 文章收藏/取消操作
export const api_set_collection = function (data) {
	return post("/api/add/collection", data)
}
// 用户的关注/取消操作
export const api_set_follow = function (data) {
	return post("/api/add/follow", data)
}
// 分页获取当前登陆用户的历史记录
export const api_get_history = function (data) {
	return post("/api/select/history", data)
}
// 根据版块id查询文章数据
export const api_ge_article_id = function (id) {
	return get("/open/get/block/" + id)
}
// 根据版块id获取版块信息
export const api_block_id = function (id) {
	return get("/open/select/block/" + id)
}
// 获取自己的所有文章
export const api_getUserAllMe_block = function () {
	return get("/api/select/article/user/me")
}
// 获取自己收藏的文章
export const api_getCollection_Article_All = function () {
	return get("/api/select/article/collection/all")
}
// 获取个人信息
export const api_user_info = function () {
	return get('/api/get/user/info')
}
// 获取关注的用户
export const api_user_follow = function () {
	return get('/api/get/follow/all')
}
// 更新用户信息
export const api_user_update_info = function (data) {
	console.log(data);
	return post("/api/update/user/info", data)
}
// 根据文章标题模糊查询
export const api_search_article = function (data) {
	return post("/open/search/article", data)
}
// 根据uid获取用户信息
export const api_user_by_uid = function (id) {
	return get("/open/get/user/info/" + id)
}
// 根据uid获取用户的文章
export const api_user_article_uid_all = function (id) {
	return get("/open/select/article/user/" + id)
}
// 留言添加
export const api_comment_add = function (data) {
	return post("/api/add/comment", data)
}
// 根据文章的id获取所有评论
export const api_comment_aid_all = function (id) {
	return get("api/get/comment/" + id)
}