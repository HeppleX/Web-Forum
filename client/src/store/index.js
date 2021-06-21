import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		token: localStorage.getItem("token"),
		head: localStorage.getItem("head"),
		nickname: localStorage.getItem("nickname"),
	},
	mutations: {
		setToken(state, data) {
			localStorage.setItem("token", data);
			state.token = data
		},
		setHead(state, data) {
			localStorage.setItem("head", data);
			state.head = data
		},
		setNickname(state, data) {
			localStorage.setItem("nickname", data);
			state.nickname = data
		}
	},
	actions: {
	},
	modules: {
	}
})
