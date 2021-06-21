<!-- 头部导航 -->
<template>
  <div class="header">
    <div class="wrapper" style="z-index: 200;">
      <div class="inner">
        <span class="logo-text" @click="setIndex('/')">部格</span>
        <div class="menu">
          <!-- 被选中颜色 -->
          <router-link class="no_link" to="/index/hot">首页</router-link>
          <router-link class="no_link" to="/block">版块</router-link>
          <router-link class="no_link" to="/history">历史</router-link>
        </div>
        <div class="nav">
          <span style="margin-right: 10px;" @click="toWirteArticle()">
            <i class="el-icon-tickets"></i>&nbsp;写文章
          </span>
          <span style="color: rgb(113,119,124,0.5);margin-right: 20px;">&nbsp;|</span>
          <div style="float: right;" v-if="token">
            <span @click="dialogVisible = true">登录</span>
            <span>·</span>
            <span @click="dialogVisible2 = true">注册</span>
          </div>
          <div style="float: right;" v-if="!token">
            <el-dropdown style="margin-top: 0.5rem;cursor: pointer;">
              <span class="el-dropdown-link">
                <el-avatar :src="head" :key="head"></el-avatar>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="toUserHome()">个人中心</el-dropdown-item>
                <el-dropdown-item @click.native="exitLogin()">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>

        <div class="search">
          <el-input
            style="width: 240px;"
            size="small"
            placeholder="搜索"
            suffix-icon="el-icon-search"
            v-model="value"
            @keyup.enter.native="search()"
          ></el-input>
        </div>
      </div>
    </div>
    <el-dialog title="登录" :visible.sync="dialogVisible" :before-close="handleClose" width="26.5rem">
      <el-input v-model="email" size="medium" placeholder="邮箱"></el-input>
      <el-input class="input" v-model="password" show-password size="medium" placeholder="密码"></el-input>
      <el-button class="input" style="width: 100%;" type="primary" @click="Login()">登录</el-button>
      <p>
        <span>没有账号?</span>
        <span
          style="margin-left: 5px;"
          class="text_link"
          @click="dialogVisible = false;dialogVisible2 = true"
        >注册</span>
      </p>
    </el-dialog>

    <el-dialog
      title="注册"
      :visible.sync="dialogVisible2"
      :before-close="handleClose"
      width="26.5rem"
    >
      <el-input v-model="name" size="medium" placeholder="昵称"></el-input>
      <el-input class="input" v-model="email" size="medium" placeholder="邮箱"></el-input>
      <el-input class="input" v-model="password" show-password size="medium" placeholder="密码"></el-input>
      <el-input class="input" v-model="password2" show-password size="medium" placeholder="确认密码"></el-input>
      <el-button class="input" style="width: 100%;" type="primary" @click="regedit()">注册</el-button>
      <p>
        <span>已有账号?</span>
        <span
          style="margin-left: 5px;"
          class="text_link"
          @click="dialogVisible2 = false;dialogVisible = true"
        >登录</span>
      </p>
    </el-dialog>

    <el-dialog title="文章搜索" :visible.sync="dialogVisible4" width="50%">
      <div class="box_list hvr-underline-from-center" v-for="item in list">
        <div>
          <span style="color: #b71ed7;font-weight: 500;">搜索</span>
          <span class="meun">·</span>
          <span class="meuns" @click="toUserInfo(item.user.uid)">{{item.user.nickname}}</span>
          <span class="meun">{{item.createDate}}</span>
          <span class="meun">·</span>
          <span class="meuns" v-if="item.block != null">{{item.block.bname}}</span>
          <span class="meun" v-else>其它</span>
        </div>
        <div style="display: flex;">
          <div style="width: 90%;">
            <div class="box_list_title" @click="toPath(item.id)">{{item.title}}</div>
            <div>
              <span class="meuns" style="margin-left: 0px;">热度:{{item.score}}</span>
              <span class="meuns" style="margin-left: 8px;">留言:378</span>
            </div>
          </div>
          <div style="float: right;">
            <img style="width: 50px; height: 50px;" :src="item.user.head" fit="fill" />
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Store from "../store/index.js";
import {
  api_Login,
  api_Reg,
  api_search_article,
  api_open_verification
} from "../api/http.js";
export default {
  name: "",
  components: {},
  data() {
    return {
      fit: "contain",
      value: "",
      // 登陆
      dialogVisible: false,
      // 注册
      dialogVisible2: false,
      // 找回
      dialogVisible3: false,
      // 搜索
      dialogVisible4: false,
      name: "",
      email: "",
      password: "",
      password2: "",
      list: [],
      form: {},
      show: true,
      count: "",
      timer: null
    };
  },
  computed: {
    token: () => {
      if (Store.state.token == "") {
        return true;
      } else {
        return false;
      }
    },
    head: () => {
      return Store.state.head;
    }
  },
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    resetPassword() {
      if (this.form.email == null || this.form.email == "") {
        this.$message("邮箱为空!");
        return false;
      }
      var emailReg = new RegExp(
        "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"
      );
      if (!emailReg.test(this.form.email)) {
        this.$message("邮箱格式错误!");
        return false;
      }
    },
    toPath(id) {
      this.dialogVisible4 = false;
      this.$router.push("/show/article/" + id);
    },
    toUserInfo(id) {
      this.dialogVisible4 = false;
      this.$router.push("/user/info/" + id);
    },
    toUserHome() {
      this.$router.push("/user/home");
    },
    // 退出登陆
    exitLogin() {
      if (this.$route.path == "/article") {
        this.$confirm("退出无法保存编辑内容，是否确认退出?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            this.$store.commit("setToken", "");
            this.$message({
              duration: 2000,
              showClose: true,
              message: "已退出登陆!",
              type: "success"
            });
            this.$router.push("/");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消"
            });
          });
      } else {
        this.$store.commit("setToken", "");
        this.$router.push("/");
      }
    },
    // 查询方法
    search() {
      api_search_article({
        title: this.value
      })
        .then(res => {
          if (res.code == 200) {
            if (res.result.length == 0) {
              this.$message("无匹配数据!");
            } else {
              this.list = res.result;
              this.dialogVisible4 = true;
            }
          }
        })
        .catch(error => {
          this.$message.error("服务器错误!");
        });
    },
    regedit() {
      var emailReg = new RegExp(
        "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"
      );
      if (!emailReg.test(this.email)) {
        this.$message("邮箱格式错误!");
        return false;
      }
      api_Reg({
        nickname: this.name,
        email: this.email,
        password: this.password,
        password2: this.password2
      })
        .then(res => {
          if (res.code == 200) {
            this.$message({
              duration: 1000,
              showClose: true,
              message: "注册成功",
              type: "success"
            });
            this.name = "";
            this.email = "";
            this.password = "";
            this.password2 = "";
            this.dialogVisible2 = false;
          } else {
            this.$message({
              duration: 1000,
              showClose: true,
              message: res.msg,
              type: "error"
            });
          }
        })
        .catch(errpr => {
          this.$message.error("服务器错误");
        });
    },
    // 登陆方法
    Login() {
      var emailReg = new RegExp(
        "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"
      );
      if (!emailReg.test(this.email)) {
        this.$message("邮箱格式错误!");
        return false;
      }
      api_Login({
        email: this.email,
        password: this.password
      })
        .then(res => {
          if (res.code == 200) {
            this.email = "";
            this.password = "";
            this.dialogVisible = false;
            console.log(res);
            // token调用vuex中的方法进行保存
            this.$store.commit("setToken", res.result.token);
            this.$store.commit("setHead", res.result.head);
            this.$store.commit("setNickname", res.result.nickname);
            console.log(this.$store.state.token);
            this.$message({
              duration: 1000,
              showClose: true,
              message: "登陆成功",
              type: "success"
            });
            this.$router.go(0);
          } else {
            this.$message({
              duration: 1000,
              showClose: true,
              message: res.msg,
              type: "error"
            });
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    setIndex(path) {
      this.$router.push(path);
    },
    // 获取当前被选中的menu
    getIndex() {
      return this.index;
    },
    // 前往写文章页面
    toWirteArticle() {
      this.$router.push("/article");
    },
    // 关闭dialog时清空数据
    handleClose(done) {
      this.email = "";
      this.password = "";
      this.password2 = "";
      this.name = "";
      done();
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {} //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style  scoped>
/* @import url(); 引入公共css类 */
.header {
  height: 5rem;
  margin-bottom: 1.3rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.wrapper {
  background-color: White;
  height: 5rem;
  border-width: 0 0 1px 0;
  border-style: solid;
  border-color: rgb(144, 147, 153, 0.3);
  position: fixed;
  width: 100%;
}
.inner {
  max-width: 960px;
  /* background-color: aquamarine; */
  height: 100%;
  margin: auto;
  line-height: 4.5rem;
  text-align: -webkit-match-parent;
}
.logo-text {
  cursor: pointer;
  height: 100%;
  font-size: 25px;
  letter-spacing: 5px;
  font-family: Helvetica, "Hiragino Sans GB", "Microsoft Yahei", "微软雅黑",
    Arial, sans-serif;
  color: #007fff;
}
.menu {
  display: inline;
}
.nav > span,
.nav > div > span {
  color: #007fff;
  font-size: 1.33rem;
  cursor: pointer;
  text-decoration: none;
  margin: initial;
  background-color: transparent;
  display: inline;
}
.menu > *:hover {
  color: #007fff;
}
.nav {
  float: right;
}
.search {
  float: right;
  margin-right: 10rem;
}
.pitch {
  color: #007fff;
}
.input {
  margin-top: 18px;
}
.link {
  color: #007fff;
  font-size: 1.33rem;
  cursor: pointer;
  text-decoration: none;
  margin: initial;
  background-color: transparent;
  margin-left: 25px;
}
.text_link {
  color: #007fff;
  cursor: pointer;
}
.no_link {
  color: #71777c;
  font-size: 1.33rem;
  cursor: pointer;
  text-decoration: none;
  margin: initial;
  background-color: transparent;
  margin-left: 20px;
}
.router-link-active {
  color: #007fff;
}
.box_list {
  padding: 1.3rem 1.5rem;
  width: 97.45%;
  border-bottom: 1px solid hsla(0, 0%, 59.2%, 0.1);
  line-height: normal;
  background-color: white;
  justify-content: flex-start;
  border-width: 0 0 1px 0;
  border-style: solid;
  border-color: rgb(144, 147, 153, 0.1);
  cursor: pointer;
}

.box_list_add {
  padding: 1.3rem 1.5rem;
  border-bottom: 1px solid hsla(0, 0%, 59.2%, 0.1);
  line-height: normal;
  background-color: white;
  justify-content: flex-start;
  border-width: 0 0 1px 0;
  border-style: solid;
  border-color: rgb(144, 147, 153, 0.1);
}

.box_list_title {
  font-size: 1.5rem;
  font-weight: bold;
  cursor: pointer;
  margin-top: 5px;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #2e3135;
}

.box_list_title:hover {
  color: #007fff;
  transition: color 0.2s;
}

.meun,
.meuns {
  margin-left: 4px;
  color: #71777c;
  font-weight: 500;
}

.meuns:hover {
  color: #007fff;
}

.box_list:hover {
  background-color: rgb(113, 119, 124, 0.1);
}

.add_list {
  color: #007fff;
  cursor: pointer;
  font-size: 1.16rem;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
* {
  font-size: 15px;
}
</style>