后端启动方式:

进入 server 文件夹: 

1. 建议用 IDEA 编辑器打开文件，自动下载依赖后可以运行代码。

2. 或者在 terminal 中执行 mvn install -Dmaven.test.skip = true 下载依赖，再用 mvn springboot:run 启动代码。


前端启动方式：

进入 client 文件夹:

1. npm install 下载依赖，完成后执行 npm run serve 就可以启动代码。

2. 或者 npm i -g yarn 安装 yarn 后, 执行 yarn 安装依赖。完成后执行 yarn run serve 启动代码。


管理端的启动方式同前端。
 
