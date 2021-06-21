module.exports = {
	// publicPath:'./',
	publicPath: './',
	devServer: {
		// open: process.platform === 'darwin',
		// host: '47.102.205.139',
		port: 9099,
		https: false,
		hotOnly: false,
		// proxy: {
		// 	"/apis": {
		// 		target: "http://47.102.205.139:8090", // 要请求的后台地址
		// 		ws: true, // 启用websockets
		// 		changeOrigin: true, // 是否跨域
		// 		pathRewrite: {
		// 			"^/apis": "" // 这里理解成用‘/api’代替target里面的地址，后面组件中我们掉接口时直接用api代替
		// 		}
		// 	}
		// },
		// before: app => {}
	}
}



