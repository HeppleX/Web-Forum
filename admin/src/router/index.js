import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '首页' },
            children:[
                {
                    path: '/home',
                    component: resolve => require(['../components/page/home.vue'], resolve),
                    meta: { title: '首页' }
                },
                {
                    path: '/404',
                    component: resolve => require(['../components/page/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../components/page/403.vue'], resolve),
                    meta: { title: '403' }
                },
				{
				    path: '/user',
				    component: resolve => require(['../components/page/User.vue'], resolve),
					 meta: { title: '用户管理' }
				},
				,
				{
				    path: '/article',
				    component: resolve => require(['../components/page/Article.vue'], resolve),
					 meta: { title: '文章管理' }
				},
				{
				    path: '/message',
				    component: resolve => require(['../components/page/Message.vue'], resolve),
					 meta: { title: '留言管理' }
				},
				{
				    path: '/circle',
				    component: resolve => require(['../components/page/Circle.vue'], resolve),
					 meta: { title: '圈子管理' }
				}
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
		
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
