/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : qinghuang

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 18/06/2020 19:36:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `u_id` bigint(20) NULL DEFAULT NULL,
  `score` int(5) NULL DEFAULT NULL,
  `b_id` bigint(20) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '使用react-hook 重写 react-redux', '<h2 data-source=\"1\"><a id=\"_0\"></a>前文</h2>\n<p data-source=\"3\">react-redux主要提供的功能是将redux和react链接起来。</p>\n<p data-source=\"5\">使用提供的connect方法可以使得任意一个react组件获取到全局的store上的状态。\n实现方法是将store存放于由provider提供的context上，在调用connect时， 就可将组件的props替换， 让其可以访问到定制化的数据或者方法。\n目标</p>\n<p data-source=\"9\">本文将尝试使用最近很火爆的react-hook来替代react-redux的基础功能。\n我们先将理想的特征列举出来，完成这些特性才算是替代了react-redux：</p>\n<h2 data-source=\"12\"><a id=\"store_11\"></a>全局维护一个store。</h2>\n<p data-source=\"14\">任何组件都可以获取到store，最好props可以定制（mapStatetoProps）。\n提供可以派发action的能力（mapDispatchtoProps）。</p>\n<p data-source=\"17\">useRudecer\n先看一下内置useRudecer的官方实例能给我们带来一些什么启示：</p>\n<pre class=\"hljs\" data-source=\"20\"><code><span class=\"hljs-keyword\">const</span> initialState = {<span class=\"hljs-attr\">count</span>: <span class=\"hljs-number\">0</span>};\n\n<span class=\"hljs-function\"><span class=\"hljs-keyword\">function</span> <span class=\"hljs-title\">reducer</span>(<span class=\"hljs-params\">state, action</span>) </span>{\n  <span class=\"hljs-keyword\">switch</span> (action.type) {\n    <span class=\"hljs-keyword\">case</span> <span class=\"hljs-string\">\'reset\'</span>:\n      <span class=\"hljs-keyword\">return</span> initialState;\n    <span class=\"hljs-keyword\">case</span> <span class=\"hljs-string\">\'increment\'</span>:\n      <span class=\"hljs-keyword\">return</span> {<span class=\"hljs-attr\">count</span>: state.count + <span class=\"hljs-number\">1</span>};\n    <span class=\"hljs-keyword\">case</span> <span class=\"hljs-string\">\'decrement\'</span>:\n      <span class=\"hljs-keyword\">return</span> {<span class=\"hljs-attr\">count</span>: state.count - <span class=\"hljs-number\">1</span>};\n    <span class=\"hljs-keyword\">default</span>:\n      <span class=\"hljs-comment\">// A reducer must always return a valid state.</span>\n      <span class=\"hljs-comment\">// Alternatively you can throw an error if an invalid action is dispatched.</span>\n      <span class=\"hljs-keyword\">return</span> state;\n  }\n}\n\n<span class=\"hljs-function\"><span class=\"hljs-keyword\">function</span> <span class=\"hljs-title\">Counter</span>(<span class=\"hljs-params\">{initialCount}</span>) </span>{\n  <span class=\"hljs-keyword\">const</span> [state, dispatch] = useReducer(reducer, {<span class=\"hljs-attr\">count</span>: initialCount});\n  <span class=\"hljs-keyword\">return</span> (\n    <span class=\"xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">div</span>&gt;</span>\n        Count: {state.count}\n        <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">button</span> <span class=\"hljs-attr\">onClick</span>=<span class=\"hljs-string\">{()</span> =&gt;</span> dispatch({type: \'reset\'})}&gt;\n            Reset\n        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">button</span>&gt;</span>\n        <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">button</span> <span class=\"hljs-attr\">onClick</span>=<span class=\"hljs-string\">{()</span> =&gt;</span> dispatch({type: \'increment\'})}&gt;+<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">button</span>&gt;</span>\n        <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">button</span> <span class=\"hljs-attr\">onClick</span>=<span class=\"hljs-string\">{()</span> =&gt;</span> dispatch({type: \'decrement\'})}&gt;-<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">button</span>&gt;</span>\n      <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">div</span>/&gt;</span>\n  );\n}</span></code></pre><p data-source=\"53\">复制代码乍一看好像react利用hook已经可以使用redux的机制了， 状态由派发的action改变，单向数据流。但是hook不会让状态共享，也就是每次useReducer保持的数据都是独立的。比如下面这个例子：</p>\n<pre class=\"hljs\" data-source=\"55\"><code><span class=\"hljs-function\"><span class=\"hljs-keyword\">function</span> <span class=\"hljs-title\">CountWrapper</span>(<span class=\"hljs-params\"></span>) </span>{\n    <span class=\"hljs-keyword\">return</span> (\n        &lt;section&gt;\n            &lt;Counter initialCount={1}/&gt;\n            &lt;Counter initialCount={1}/&gt;\n        &lt;/setion&gt;\n        )\n}</code></pre><p data-source=\"66\">复制代码两个Count组件内部的数据是独立的，无法互相影响，状态管理也就无从说起。 究其原因，useReducer内部也是用useState实现的</p>\n<pre class=\"hljs\" data-source=\"68\"><code><span class=\"hljs-function\"><span class=\"hljs-keyword\">function</span> <span class=\"hljs-title\">useReducer</span>(<span class=\"hljs-params\">reducer, initialState</span>) </span>{\n  <span class=\"hljs-keyword\">const</span> [state, setState] = useState(initialState);\n\n  <span class=\"hljs-function\"><span class=\"hljs-keyword\">function</span> <span class=\"hljs-title\">dispatch</span>(<span class=\"hljs-params\">action</span>) </span>{\n    <span class=\"hljs-keyword\">const</span> nextState = reducer(state, action);\n    setState(nextState);\n  }\n\n  <span class=\"hljs-keyword\">return</span> [state, dispatch];\n}</code></pre><p data-source=\"81\">复制代码StoreProvider\nuseReducer看来并不能帮上忙。\n解决全局状态的问题可以参照react-redux的做法，提供一个Provider，使用context的方式来做。\n这里可以使用useContext，这个内置的hook。</p>\n', 1, 0, 1, '2020-06-18 19:03:55', '2020-06-18 19:03:55');
INSERT INTO `article` VALUES (2, '听说你还不懂React Hook？', '<p data-source=\"1\">从官网的这句话中，我们可以明确的知道，Hook增加了函数式组件中state的使用，在之前函数式组件是无法拥有自己的状态，只能通过props以及context来渲染自己的UI，而在业务逻辑中，有些场景必须要使用到state，那么我们就只能将函数式组件定义为class组件。而现在通过Hook，我们可以轻松的在函数式组件中维护我们的状态，不需要更改为class组件。</p>\n<p data-source=\"3\">React Hooks要解决的问题是状态共享，这里的状态共享是指只共享状态逻辑复用，并不是指数据之间的共享。我们知道在React Hooks之前，解决状态逻辑复用问题，</p>\n<p data-source=\"5\">我们通常使用higher-order components和render-props，那么既然已经有了这两种解决方案，为什么React开发者还要引入React Hook？对于higher-order components和render-props，React Hook的优势在哪？\nReact Hook例子</p>\n<p data-source=\"8\">我们先来看一下React官方给出的React Hook的demo</p>\n<pre class=\"hljs\" data-source=\"10\"><code><span class=\"hljs-keyword\">import</span> { useState } <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">\'React\'</span>;\n\n<span class=\"hljs-function\"><span class=\"hljs-keyword\">function</span> <span class=\"hljs-title\">Example</span>(<span class=\"hljs-params\"></span>) </span>{\n  <span class=\"hljs-comment\">// Declare a new state variable, which we\'ll call \"count\"</span>\n  <span class=\"hljs-keyword\">const</span> [count, setCount] = useState(<span class=\"hljs-number\">0</span>);\n\n  <span class=\"hljs-keyword\">return</span> (\n    <span class=\"xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">div</span>&gt;</span>\n      <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">p</span>&gt;</span>You clicked {count} times<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">p</span>&gt;</span>\n      <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">button</span> <span class=\"hljs-attr\">onClick</span>=<span class=\"hljs-string\">{()</span> =&gt;</span> setCount(count + 1)}&gt;\n        Click me\n      <span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">button</span>&gt;</span>\n    <span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">div</span>&gt;</span></span>\n  );\n}\n复制代码我们再来看看不用React Hook的话，如何实现\n<span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">Example</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">React</span>.<span class=\"hljs-title\">Component</span> </span>{\n  <span class=\"hljs-keyword\">constructor</span>(props) {\n    <span class=\"hljs-keyword\">super</span>(props);\n    <span class=\"hljs-keyword\">this</span>.state = {\n      <span class=\"hljs-attr\">count</span>: <span class=\"hljs-number\">0</span>\n    };\n  }\n\n  render() {\n    <span class=\"hljs-keyword\">return</span> (\n      <span class=\"xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">div</span>&gt;</span>\n        <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">p</span>&gt;</span>You clicked {this.state.count} times<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">p</span>&gt;</span>\n        <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">button</span> <span class=\"hljs-attr\">onClick</span>=<span class=\"hljs-string\">{()</span> =&gt;</span> this.setState({ count: this.state.count + 1 })}&gt;\n          Click me\n        <span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">button</span>&gt;</span>\n      <span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">div</span>&gt;</span></span>\n    );\n  }\n}</code></pre><p data-source=\"47\">复制代码可以看到，在React Hook中，class Example组件变成了函数式组件，但是这个函数式组件却拥有的自己的状态，同时还可以更新自身的状态。这一切都得益于useState这个Hook，useState 会返回一对值：当前状态和一个让你更新它的函数，你可以在事件处理函数中或其他一些地方调用这个函数。它类似 class 组件的 this.setState，但是它不会把新的 state 和旧的 state 进行合并\nReact复用状态逻辑的解决方案\nHook是另一种复用状态逻辑的解决方案，React开发者一直以来对状态逻辑的复用方案不断提出以及改进，从Mixin到高阶组件到Render Props 到现在的Hook，我们先来简单了解一下以前的解决方案。</p>\n', 1, 0, 1, '2020-06-18 19:05:46', '2020-06-18 19:05:46');
INSERT INTO `article` VALUES (3, '开发中一定需要 service 接口和 serviceImpl 吗?', '<p data-source=\"1\">开发中一定需要 service 接口和 serviceImpl 吗</p>\n<p data-source=\"3\">感觉不用接口直接写实现类开发更加方便。</p>\n', 2, 0, 2, '2020-06-18 19:07:49', '2020-06-18 19:07:49');
INSERT INTO `article` VALUES (4, '求推荐 Spring demo 项目', '<p data-source=\"1\">最近在看 Spring/SpringBoot, 有谁能推荐几个 github 上中等规模的项目拿来学习学习的吗？</p>\n<p data-source=\"3\">谢谢。</p>\n', 2, 0, 2, '2020-06-18 19:08:46', '2020-06-18 19:08:46');
INSERT INTO `article` VALUES (5, '感觉国内用 vue 的比 react 要多呢？', '<p data-source=\"1\">身边的人基本没有用 react 的，包括面试的时候，发现大部分公司用的都是 vue。</p>\n<p data-source=\"3\">用过一段时间的 react 和 vue，考虑要不要入手 angular 试一试。</p>\n<p data-source=\"5\">前端目前就三大框架，考虑要不要再入手一个 angular。把框架学了，可以总结其中的各个特点。</p>\n<p data-source=\"7\">我是从vue转的react</p>\n<p data-source=\"9\">突然想到要学习什么？所以问一问。</p>\n<p data-source=\"11\">顺便提一下自己写的react的插件，目前考虑要不要写个vue版本的。</p>\n<ul data-source=\"13\"><li>\n<p data-source=\"13\"><a href=\"https://md.kkfor.com/\">在线预览</a></p>\n</li>\n<li>\n<p data-source=\"15\"><a href=\"https://github.com/kkfor/for-editor\">源码地址</a></p>\n</li>\n</ul>\n', 2, 0, 4, '2020-06-18 19:11:31', '2020-06-18 19:11:31');
INSERT INTO `article` VALUES (6, '字节跳动-前端工程师(社招 & 校招 & 实习生)', '<p data-source=\"1\">团队目前所做的业务面向国内&amp;海外，产品类型有 社区 / 社交 / 在线教育 / 基础工程 等等，同时也有部分同学从事 Web Infra 相关的技术工作。</p>\n<p data-source=\"3\">我们希望你：</p>\n<ul data-source=\"5\"><li><p data-source=\"5\">有相对扎实的计算机基础，了解基本的数据结构和算法，有一定的编程经验（重点：不需要刻意刷题）</p></li>\n<li><p data-source=\"6\">有不错的前端编程基础，了解 JavaScript 常用的一些基本 API，熟悉异步编程的处理思路，熟悉 CSS 基本的一些用法</p></li>\n<li><p data-source=\"7\">对于 React 全家桶、TypeScript、Node.js 等需要有一个基本的了解，工作中大量用到</p></li>\n<li><p data-source=\"8\">对于技术有热情，务实，自驱</p></li>\n</ul>\n<p data-source=\"10\">在这里你可以获得：</p>\n<p data-source=\"12\">参与从零开始打造一款新产品的机会\n免费的一日三餐和健身（好像是大部分公司的标配）以及住宿补贴\n社招的薪资福利 20K - 50K，详情请参考 BOSS 直聘上面的信息，校招的薪资福利今年还没确定，实习生每日¥400\n工作地点：</p>\n<p data-source=\"17\">北京：北京市海淀区北三环西路43号中航广场1号楼\n上海：上海闵行区科技绿洲3期24号楼\n杭州：余杭区EFC欧美金融城T5（未来搬迁到：余杭区西溪八方城）\n广州：广州市天河区天盈广场\n简历投递方式：\n邮箱：xieweicheng@bytedance.com\n格式：邮件请附上简历，同时主题请写:【简历】-【岗位】</p>\n<p data-source=\"25\">职位描述：</p>\n<p data-source=\"27\">负责前端技术选型和开发工作；\n优化前端功能设计，解决各种浏览器和终端设备的兼容性问题；\n通过技术手段，提升用户体验并满足高性能要求；\n通用组件、类库编写，提升开发效率和质量。\n职位要求：</p>\n<ul data-source=\"33\"><li><p data-source=\"33\">本科及以上学历，计算机相关专业；</p></li>\n<li><p data-source=\"34\">精通 HTML、CSS、JS，熟悉页面架构和布局，熟悉 HTML5/CSS3 等常用技术；</p></li>\n<li><p data-source=\"35\">熟悉常用 UI 框架（如 bootstrap/pure/kendo 等);</p></li>\n<li><p data-source=\"36\">精通 JavaScript、AJAX、DOM、jQuery 等技术；</p></li>\n<li><p data-source=\"37\">熟悉 Node.js，熟练使用 Grunt、Gulp、Webpack 等构建工具；</p></li>\n<li><p data-source=\"38\">具备 MVVM 框架开发经验，如 React、Vue.js、AngularJS 等；</p></li>\n<li><p data-source=\"39\">良好的沟通和团队协作能力、热爱技术、责任心强、能推动技术框架的落地使用。</p></li>\n</ul>\n', 3, 0, 3, '2020-06-18 19:13:59', '2020-06-18 19:13:59');
INSERT INTO `article` VALUES (7, ' nest.js框架+mysql+vue+element ui项目开发人员', '<p data-source=\"1\">我是nodejs爱好者，用nest.js+mysql和前端用vue+element ui开发了一个pc端的crm软件，现在人手不足，希望可以有兴趣的朋友联系，项目分成，待遇从优。</p>\n<p data-source=\"3\">我在广州，希望能找到同在广州的开发者一起研究学习，nodejs，一起做项目。有兴趣的伙伴可以联系我，或加微信。</p>\n<p data-source=\"5\">–已结贴–</p>\n<p data-source=\"7\">感谢社区兄弟们支持和帮助。在寻找伙伴的过程中会遇到形形色色的人，最终还是找到一些志同道合的伙伴。感谢社区，感谢Node。</p>\n<p data-source=\"9\">祝社区越做越好！</p>\n', 3, 0, 3, '2020-06-18 19:14:59', '2020-06-18 19:14:59');
INSERT INTO `article` VALUES (8, 'Deno + mongodb 实战踩坑记', '<p data-source=\"1\">自从 deno 1.0 发布以来，有关 deno 的文章很多，大多数都是在讨论怎么安装 deno 、deno 有哪些特点 、deno 和 node 有哪些异同、deno是不是 node 的替代品等。咱们今天不讨论这些，毕竟 Talk is cheap. Show me the code！ 亲自体验一把 deno 开发带来的快感、用 deno 搞一个“企业级”应用：deno-supermarket，难道不香吗？</p>\n<h3 data-source=\"3\"><a id=\"deno__2\"></a>deno 常见的一些坑</h3>\n<p data-source=\"5\">在实战之前，还是先来介绍几个我在刚接触 deno 时遇到的小坑。\n权限标志符位置的问题\n我们都知道， deno 默认是安全的，就是导致了默认情况下是不允许访问网络、读写文件等。比如有个名为 index.ts 的文件内容如下：</p>\n<pre class=\"hljs\" data-source=\"9\"><code><span class=\"hljs-keyword\">import</span> { serve } <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">\"https://deno.land/std@0.50.0/http/server.ts\"</span>;\n<span class=\"hljs-keyword\">const</span> s = serve({ port: <span class=\"hljs-number\">8000</span> });\n<span class=\"hljs-built_in\">console</span>.log(<span class=\"hljs-string\">\"http://localhost:8000/\"</span>);\n<span class=\"hljs-keyword\">for</span> <span class=\"hljs-keyword\">await</span> (<span class=\"hljs-keyword\">const</span> req of s) {\n  req.respond({ body: <span class=\"hljs-string\">\"Hello World\\n\"</span> });\n}</code></pre><p data-source=\"17\">复制代码如果直接执行 deno run index.ts, 会报错：</p>\n<p data-source=\"19\">error: Uncaught PermissionDenied: network access to “0.0.0.0:8000”, run again with the --allow-net flag</p>\n<p data-source=\"21\">复制代码所以我们很自然的就会在启动命令的最后加上 --allow-net ，如下：</p>\n<p data-source=\"23\">deno run index.ts --allow-net</p>\n<p data-source=\"25\">复制代码但是，这样仍然会报错。查了资料才知道 ，–allow-net 、–allow-read 之类的标志是不可以放到文件名后面的，必须紧跟在 deno run 后面，比如，如下才是正确的：</p>\n<p data-source=\"27\">deno run --alow-net index.ts</p>\n<p data-source=\"29\">复制代码为什么调换了位置就不行呢？ issue 上的回答是，如果 --allow-net 跟在文件名后面，是传给 js  脚本的，而不是传给 deno 的。想了解更多的，可以看这个 confused by order of cli option flags。反正，记住一点就行：权限标志一定要跟在 deno run 后面！</p>\n<p data-source=\"31\">因为我们前端同学大多数平时很少写后台，不太清楚安全的重要性，为了避免遇到各种权限问题，我建议平时在写一些练手项目时，直接用 deno run -A 来启用全部的权限。（这只是方便调试，在生产环境中一定要慎用！）\n不稳定的 API</p>\n<p data-source=\"34\">因为实战过程中使用了 mongodb , 所以需要引入 Deno 的第三方模块 mongo，然而在启动项目会报错</p>\n', 1, 0, 1, '2020-06-18 19:33:30', '2020-06-18 19:33:30');

-- ----------------------------
-- Table structure for block
-- ----------------------------
DROP TABLE IF EXISTS `block`;
CREATE TABLE `block`  (
  `b_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `b_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b_introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b_score` int(5) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attention` int(5) NULL DEFAULT NULL,
  `number` int(5) NULL DEFAULT NULL,
  PRIMARY KEY (`b_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of block
-- ----------------------------
INSERT INTO `block` VALUES (1, '技术', '技术让生活更美好。', 0, '2020-06-11 16:47:27', '2020-06-11 16:47:27', 'http://img0.imgtn.bdimg.com/it/u=2288454780,1874896818&fm=26&gp=0.jpg', NULL, NULL);
INSERT INTO `block` VALUES (2, '问答', '解决你在学习中，工作中存在的疑惑。', 0, '2020-06-11 16:49:47', '2020-06-11 16:49:47', 'http://img4.imgtn.bdimg.com/it/u=901608395,1660019621&fm=26&gp=0.jpg', NULL, NULL);
INSERT INTO `block` VALUES (3, '工作', '为各位 HR 和求职者提供便利。', 0, '2020-06-11 16:51:56', '2020-06-11 16:51:56', 'http://img2.imgtn.bdimg.com/it/u=2385060781,397190899&fm=11&gp=0.jpg', NULL, NULL);
INSERT INTO `block` VALUES (4, '想法', '快来分享你的创意想法吧。', 0, '2020-06-11 16:52:53', '2020-06-11 16:52:53', 'http://img2.imgtn.bdimg.com/it/u=2397692959,635282857&fm=26&gp=0.jpg', NULL, NULL);

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL,
  `a_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collection
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_id` bigint(20) NULL DEFAULT NULL,
  `a_id` bigint(20) NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL,
  `by_uid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follow
-- ----------------------------

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL,
  `aid` bigint(20) NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of history
-- ----------------------------

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL,
  `a_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of likes
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `u_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `u_introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` int(5) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'aqqq', 'aqqq@mail.com', 'aqqq', 'web 前端工程师', 'common', 0, '2020-06-18 18:42:05', '2020-06-18 18:42:05', 'http://img4.imgtn.bdimg.com/it/u=3630137643,3614073041&fm=26&gp=0.jpg');
INSERT INTO `user` VALUES (2, 'qwqq', 'qwqq@mail.com', 'qwqq', '擅长 java 开发', 'common', 0, '2020-06-18 18:50:58', '2020-06-18 18:50:58', 'http://img2.imgtn.bdimg.com/it/u=3132978919,2546842163&fm=11&gp=0.jpg');
INSERT INTO `user` VALUES (3, 'edww', 'edww@mail.com', 'edww', 'python开发，AI，爬虫', 'common', 0, '2020-06-18 18:55:14', '2020-06-18 18:55:14', 'http://img2.imgtn.bdimg.com/it/u=3480771704,997995424&fm=26&gp=0.jpg');

SET FOREIGN_KEY_CHECKS = 1;
