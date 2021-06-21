import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import { Message } from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import leMarkdownEditor from 'le-markdown-editor'
import './assets/icon/iconfont.css'
Vue.use(leMarkdownEditor)
Vue.use(ElementUI);
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
	// title 设置
	if (to.meta.flag && store.state.token == "") {
		ElementUI.Message('请先登陆')
		return false
	}
	if (to.meta.title) {
		window.document.title = to.meta.title + "-部格"
	} else {
		window.document.title = "部格"
	}
	next()
})


router.afterEach((to, from) => {
	// console.log('路由后')
})
new Vue({
	router,
	store,
	render: h => h(App)
}).$mount('#app')
