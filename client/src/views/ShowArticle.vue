<!-- 文章显示页面 -->
<template>
  <div class="talk">
    <div class="box">
      <div class="box-left">
        <div class="content">
          <h2 style="text-align: center;margin-top: 5px;">{{article.title}}</h2>
          <div style="text-align: center">
            <span class="meuns" @click="toUserInfo(article.user.uid)">{{author}}</span>
            <span class="meun">·</span>
            <span class="meun">{{article.createDate}}</span>
            <span class="meun">·</span>
            <span
              class="meuns"
              v-if="article.block != null"
              @click="toBlockInfo(article.block.bid)"
            >{{bname}}</span>
            <span class="meun" v-else>其它</span>
          </div>
          <div v-html="article.content"></div>
        </div>
        <br />
        <div class="comment">
          <el-card>
            <p style="text-align: center;color: #909399;margin-top: -5px;">评论</p>
            <div style="display: flex">
              <el-input type="textarea" rows="2" v-model="value" placeholder="输入你的评论"></el-input>
              <div>
                <el-button
                  type="primary"
                  style="float: right; margin-left: 1.25rem"
                  @click="addMessage()"
                >评论</el-button>
              </div>
            </div>
            <br />
            <br />
            <div style="display: flex;margin-bottom: 10px;" v-for="item in list">
              <div>
                <el-avatar size="large" :src="item.user.head"></el-avatar>
              </div>
              <div style="margin-left: 10px;">
                <div>
                  <span class="meuns" @click="toUserInfo(item.user.uid)">{{item.user.nickname}}</span>
                  <span class="meun">{{item.createDate}}</span>
                </div>
                <div>
                  <p
                    class="message"
                    style="margin-top: -1px;font-size: 1.2rem;margin-left: 3px;"
                  >{{item.content}}</p>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      </div>
      <div class="box-right">
        <!-- <div class="smate"> -->
        <!-- 三目运算，属性绑定 -->
        <div
          :class="[flag1?'block_menu menu_link ':'block_menu ']"
          style="margin-top: 12rem;"
          @click="setFlag1()"
        >
          <span class="block_menu_text">
            <i class="iconfont bugedianzan"></i>
          </span>
        </div>
        <div :class="[flag2?'block_menu menu_link ':'block_menu ']" @click="setFlag2()">
          <span class="block_menu_text">
            <i class="iconfont bugeshoucang"></i>
          </span>
        </div>
        <div
          v-show="nickname!==author"
          :class="[flag3?'block_menu menu_link ':'block_menu'] "
          @click="setFlag3()"
        >
          <span class="block_menu_text">
            <i class="iconfont bugeguanzhu"></i>
          </span>
        </div>
        <!-- </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import AuthorInfo from "./AuthorInfo.vue";
import vuex from "../store/index.js";
import {
  api_Open_article_id,
  api_Open_enter,
  api_set_like,
  api_set_collection,
  api_set_follow,
  api_comment_add,
  api_comment_aid_all
} from "../api/http.js";
export default {
  name: "",
  components: {
    AuthorInfo
  },
  data() {
    return {
      list: [],
      value: "",
      // 文章id
      id: "",
      article: {},
      author: "",
      bname: "",
      // 这个是判断是否 已收藏 已点赞 已关注 ，根据情况修改样式
      flag1: false,
      flag2: false,
      flag3: false
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
    toUserInfo(id) {
      this.$router.push("/user/info/" + id);
    },
    toBlockInfo(id) {
      this.$router.push("/details/" + id);
    },
    addMessage() {
      if (vuex.state.token == "") {
        this.$message("请先登陆!");
        return false;
      }
      api_comment_add({
        aId: this.id,
        content: this.value
      })
        .then(res => {
          if (res.code == 200) {
            this.value = "";
            this.list.push(res.result);
            this.$message.success(res.msg);
          }
        })
        .catch(error => {
          this.$message.error("服务器错误!");
        });
    },
    // 点赞操作
    setFlag1() {
      if (vuex.state.token == "") {
        this.$message("请先登陆!");
      } else {
        api_set_like({ aid: this.id })
          .then(res => {
            if (res.code == 200) {
              this.$message({
                duration: 1000,
                showClose: true,
                message: res.msg,
                type: "success"
              });
              this.flag1 = !this.flag1;
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(error => {
            this.$message.error("服务器错误!");
          });
      }
    },
    // 收藏操作
    setFlag2() {
      if (vuex.state.token == "") {
        this.$message("请先登陆!");
      } else {
        api_set_collection({ aid: this.id })
          .then(res => {
            if (res.code == 200) {
              this.$message({
                duration: 1000,
                showClose: true,
                message: res.msg,
                type: "success"
              });
              this.flag2 = !this.flag2;
            } else {
              this.$message(res.msg);
            }
          })
          .catch(error => {
            this.$message.error("服务器错误!");
          });
      }
    },
    // 关注操作
    setFlag3() {
      if (vuex.state.token == "") {
        this.$message("请先登陆!");
      } else {
        api_set_follow({ aid: this.id })
          .then(res => {
            if (res.code == 200) {
              this.$message({
                duration: 1000,
                showClose: true,
                message: res.msg,
                type: "success"
              });
              this.flag3 = !this.flag3;
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(error => {
            this.$message("服务器错误!");
          });
      }
    },
    init() {
      api_Open_article_id(this.id).then(res => {
        if (res.code == 200) {
          this.article = res.result;
          this.author = res.result.user.nickname;
          this.bname = res.result.block.bname;
        } else {
          this.$message.error("服务器错误!");
        }
      });
    },
    getFlags() {
      api_Open_enter(this.id)
        .then(res => {
          if (res.code == 200) {
            // console.log(res);
            this.flag1 = res.result.like;
            this.flag2 = res.result.collection;
            this.flag3 = res.result.follow;
          } else {
            this.flag1 = false;
            this.flag2 = false;
            this.flag3 = false;
          }
        })
        .catch(error => {
          this.$message.error("服务器错误!");
        });
    },
    initMessage() {
      api_comment_aid_all(this.id)
        .then(res => {
          if (res.code == 200) {
            this.list = res.result;
            // console.log(this.list);
          }
        })
        .catch(error => {
          console.log(error);
          this.$message.error("服务器错误!");
        });
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    // 每次进入该页面到顶部
    window.scrollTo(0, 0);
    // 获取文章id
    this.id = this.$route.params.id;
    this.initMessage();
    console.log("页面里获取的id---" + this.id);
    this.init();
    this.getFlags();
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
/* @import url(); 引入公共css类 */
.meun,
.meuns {
  margin-left: 4px;
  color: #71777c;
  font-size: 14px;
}
.meuns:hover {
  cursor: pointer;
  color: #007fff;
}
.box {
  margin: auto;
  max-width: 900px;
  display: flex;
  justify-content: flex-start;
  margin-bottom: 30px;
}
.box-left {
  width: 95%;
  /* text-indent: 20px; */
  /* padding: 20px;
  margin: 0 5px 0 5px; */
}
.content {
  background-color: white;
  /* border: 1px solid #ebeef5; */
  border-radius: 5px;
  text-indent: 20px;
  padding: 20px;
  margin: 0 1rem;
}
.comment {
  /* max-width: 78%; */
  padding: 20px;
  margin: 0 1rem;
}

.box-right {
  position: fixed;
  left: 84.5%;
  /* border: 1px solid; */
}
.left-menu {
  width: 7rem;
  position: fixed;
  background-color: White;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.right-menu {
  background-color: White;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.textarea {
  width: 90%;
  margin: auto;
}

.input_text::-webkit-input-placeholder {
  background-color: red;
} /*webkit 内核浏览器*/
.textarea_menu {
  margin-left: 10px;
}
.link {
  cursor: pointer;
}
.smate {
  position: fixed;
}
.block_menu {
  border-radius: 50%;
  width: 45px;
  height: 45px;
  background: white;
  margin: auto;
  margin-top: 2rem;
  text-align: center;
  cursor: pointer;
}
.block_menu:hover {
  background-color: rgb(0, 127, 255, 0.7);
  transition: background-color 0.5s;
  color: white;
}
/* 已点赞 已关注 已收藏 的样式 */
.menu_link {
  border-radius: 50%;
  width: 45px;
  height: 45px;
  background: white;
  margin: auto;
  margin-top: 2rem;
  text-align: center;
  cursor: pointer;
  background-color: rgb(0, 127, 255, 0.7);
  color: white;
}
.block_menu_text {
  position: relative;
  top: 8px;
}
.block_menu_text > i {
  font-size: 25px;
}
</style>
