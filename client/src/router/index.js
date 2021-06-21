import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		redirect: 'index/hot',
	},
	{
		path: '/index',
		name: 'Home',
		component: Home,
		children: [
			{
				path: 'hot',
				name: '热门',
				meta: {
					title: '首页-热门'
				},
				component: () => import('../views/ListArticle.vue'),
			},
			{
				path: 'new',
				name: '最新',
				meta: {
					title: '首页-最新'
				},
				component: () => import('../views/ListArticle.vue'),
			},
			{
				path: 'attention',
				name: '关注',
				meta: {
					title: '首页-关注',
					flag: true
				},
				component: () => import('../views/ListArticle.vue'),
			}
		]
	},
	{
		path: '/article',
		name: '写文章',
		meta: {
			title: '写文章',
			flag: true
		},
		component: () => import('../views/CreatArticle.vue'),
		children: [
		]
	},
	{
		path: '/block',
		name: '版块',
		meta: {
			title: '版块'
		},
		component: () => import('../views/Block.vue'),
		children: [

		]
	},
	{
		path: '/show/article/:id',
		name: '文章',
		meta: {
			title: '文章'
		},
		component: () => import('../views/ShowArticle.vue'),
		children: [
		]
	},
	{
		path: '/details/:id',
		name: '版块详情',
		meta: {
			title: '版块详情'
		},
		component: () => import('../views/BlockInfo.vue'),
	},
	{
		path: '/history',
		name: '历史',
		meta: {
			title: '历史',
			flag: true
		},
		component: () => import('../views/History.vue'),
	},
	{
		path: '/user/home',
		name: '个人中心',
		meta: {
			title: '个人中心',
			flag: true
		},
		component: () => import('../views/UserHome.vue'),
	},
	{
		path: '/search',
		name: '文章搜索',
		meta: {
			title: '文章搜索',
		},
		component: () => import('../views/Search.vue'),
	},
	{
		path: '/user/info/:id',
		name: '信息查看',
		meta: {
			title: '信息查看',
		},
		component: () => import('../views/UserInfo.vue'),
	}


]

const router = new VueRouter({
	// mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
