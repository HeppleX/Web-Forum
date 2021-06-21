<!-- 观看他人信息页面  -->
<template>
  <div class>
    <div class="header">
      <el-card>
        <div style="float: none;margin-bottom: 12rem;">
          <div style="display: inline;float: left;">
            <img
              :src="user.head? user.head: '../assets/head.jpg'"
              style="width:12rem;height:12rem;"
              fit="fill"
              :onerror="defaultImg"
            />
          </div>
          <div class="right">
            <span class="name">{{user.nickname}}</span>
            <span
              style="background-color:aquamarine;color: white;font-family:'幼圆';padding: 0 6px;margin-left: 6px;font-size: 15px;"
            >LV{{user.score/10}}</span>
            <br />
            <p>{{user.uintroduce}}</p>
            <br />
            <el-button
              v-show="nickname!==this.user.nickname"
              style="padding:8px 20px"
              @click="setAttention()"
            >{{ flagText }}</el-button>
          </div>
        </div>
      </el-card>
      <br />
      <el-card>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="他的文章" name="first">
            <div class="box_list hvr-underline-from-center" v-for="item in list">
              <div>
                <span style="color: #b71ed7;font-weight: 500;">我的文章</span>
                <span class="meun">·</span>
                <span class="meuns">{{item.user.nickname}}</span>
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
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>

<script>
import {
  api_getUserAllMe_block,
  api_user_article_uid_all,
  api_Open_enter,
  api_user_by_uid,
  api_user_follow,
  api_set_follow,
  api_user_update_info
} from "../api/http.js";
import vuex from "../store/index.js";
import avatar from "../assets/head.jpg";
export default {
  components: {},
  data() {
    return {
      id: "",
      form: {},
      user: "",
      flagText: "+ 关注",
      name: "",
      list: [],
      defaultImg: 'this.src="' + avatar + '"',
      activeName: "first"
    };
  },
  //计算属性
  computed: {
    nickname: () => {
      return vuex.state.nickname;
    }
  },
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    toPath(id) {
      this.$router.push("/show/article/" + id);
    },
    handleClick(tab, event) {
      console.log(tab.label);
      if (tab.label == "他的文章") {
        this.initArticle();
      }
    },
    initArticle() {
      api_user_article_uid_all(this.id)
        .then(res => {
          if (res.code == 200) {
            this.name = "他的文章";
            this.list = res.result;
            this.getAttention(this.list[0].id);
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    init() {
      api_user_by_uid(this.id)
        .then(res => {
          if (res.code == 200) {
            this.user = res.result;
            this.form = res.result;
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    getAttention(id) {
      api_Open_enter(id)
        .then(res => {
          if (res.code == 200) {
            this.flagText = res.result.follow ? "已关注" : "+ 关注";
          } else {
            this.flagText = "+ 关注";
          }
        })
        .catch(err => {
          this.$message.error(err.message);
        });
    },
    // 关注操作
    setAttention() {
      if (vuex.state.token == "") {
        this.$message("请先登陆!");
      } else {
        api_set_follow({ aid: this.list[0].id })
          .then(res => {
            if (res.code == 200) {
              this.$message({
                duration: 1000,
                showClose: true,
                message: res.msg,
                type: "success"
              });
              this.flagText = this.flagText === "已关注" ? "+ 关注" : "已关注";
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(error => {
            this.$message("服务器错误!");
          });
      }
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.id = this.$route.params.id;
    // 初始化作者的文章内容
    this.initArticle();
    // 初始化获取作者的信息
    this.init();
  },
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
<style scoped>
.header {
  width: 80rem;
  margin: auto;
}

.right {
  float: left;
  margin-left: 20px;
  width: 60rem;
}

.name {
  color: #000;
  font-weight: 600;
  font-size: 2.167rem;
  line-height: 1.2;
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
</style>
