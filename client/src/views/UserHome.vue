<!--  -->
<template>
  <div class>
    <div class="header">
      <el-dialog title="个人信息修改" :visible.sync="dialogVisible" width="30%">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="昵称">
            <el-input v-model="form.nickname"></el-input>
          </el-form-item>
          <el-form-item label="签名">
            <el-input type="textarea" v-model="form.uintroduce"></el-input>
          </el-form-item>
          <el-form-item label="头像">
            <el-input v-model="form.head" placeholder="填入图片的url地址"></el-input>
            <!-- <el-upload
              class="avatar-uploader"
              action="http://127.0.0.1:8098/open/web/file/insert"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            > -->
              <img v-if="form.head" :src="form.head" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUser()">确 定</el-button>
        </span>
      </el-dialog>

      <el-card>
        <div style="float: none;margin-bottom: 12rem;">
          <div style="display: inline;float: left;">
            <img 
            :src=" user.head? user.head: ''" 
            style="width:12rem;height:12rem;" 
            fit="fill" 
            :onerror="defaultImg"/>
          </div>
          <div class="right">
            <span class="name">{{user.nickname}}</span>
            <span
              style="background-color:aquamarine;padding:0 6px;color: white;font-family:'幼圆';margin-left: 6px;font-size: 15px;"
            >LV{{user.score/10}}</span>
            <br />
            <p>{{user.uintroduce}}</p>
            <el-button
              style="float: right;"
              type="text"
              size="mini"
              plain
              @click="dialogVisible = true"
            >信息修改</el-button>
          </div>
        </div>
      </el-card>
      <br />
      <el-card>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="我的文章" name="first">
            <div class="box_list hvr-underline-from-center" v-for="item in list">
              <div>
                <span style="color: #b71ed7;font-weight: 500;">我的文章</span>
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
                  <img
                    style="width: 50px; height: 50px;"
                    :src="item.user.head"
                    fit="fill"
                  />
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="我的收藏" name="second">
            <div class="box_list hvr-underline-from-center" v-for="item in list">
              <div>
                <span style="color: #b71ed7;font-weight: 500;">我的收藏</span>
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
                  <img
                    style="width: 50px; height: 50px;"
                    :src="item.user.head"
                    fit="fill"
                  />
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="我的关注" name="third">
            <div class="box_list hvr-underline-from-center" v-for="item in userList">
              <div>
                <span style="color: #b71ed7;font-weight: 500;">我的关注</span>
                <span class="meun">·</span>
                <span
                  style="background-color:aquamarine;color: white;padding: 0 6px;font-family:'幼圆';margin-left: 6px;font-size: 10px;"
                >LV{{item.score/10}}</span>
                <!-- <span class="meun">{{item.createDate}}</span>
								<span class="meun">·</span>
								<span class="meuns" v-if="item.block != null">{{item.block.bname}}</span>
                <span class="meun" v-else>其它</span>-->
              </div>
              <div style="display: flex;">
                <div style="width: 90%;">
                  <div class="box_list_title" @click="toUserInfo(item.uid)">{{item.nickname}}</div>
                  <span class="meun">{{item.uintroduce}}</span>
                  <!-- <div>
										<span class="meuns" style="margin-left: 0px;">热度:{{item.score}}</span>
										<span class="meuns" style="margin-left: 8px;">留言:378</span>
                  </div>-->
                </div>
                <div style="float: right;">
                  <img
                    style="width: 50px; height: 50px;"
                    :src="item.head"
                    fit="fill"
                  />
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
import Store from "../store/index.js";
import {
  api_getUserAllMe_block,
  api_getCollection_Article_All,
  api_user_info,
  api_user_follow,
  api_user_update_info
} from "../api/http.js";
import avatar from '../assets/head.jpg';

export default {
  components: {},
  data() {
    return {
      form: {},
      dialogVisible: false,
      user: "",
      name: "",
      list: [],
      userList: [],
      activeName: "first",
      defaultImg: 'this.src="' + avatar + '"',
      url:
        "https://upload.jianshu.io/users/upload_avatars/18781452/34094372-45e4-42be-8b5a-c8d0bc1b825b?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"
    };
  },
  //计算属性
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    toUserInfo(id) {
      this.$router.push("/user/info/" + id);
    },
    updateUser() {
      api_user_update_info({
        nickname: this.form.nickname,
        uIntroduce: this.form.uintroduce,
        head: this.form.head
      })
        .then(res => {
          if (res.code == 200) {
            this.$message.success(res.msg);
            this.dialogVisible = false;
            this.$store.commit("setHead", this.form.head);
            this.$store.commit("setNickname", this.form.nickname);
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    toPath(id) {
      this.$router.push("/show/article/" + id);
    },
    handleClick(tab, event) {
      console.log(tab.label);
      if (tab.label == "我的文章") {
        this.initArticle();
      }
      if (tab.label == "我的收藏") {
        this.initCollect();
      }
      if (tab.label == "我的关注") {
        this.initAttention();
      }
    },
    init() {
      api_user_info()
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
    // 我的收藏
    initCollect() {
      api_getCollection_Article_All()
        .then(res => {
          if (res.code == 200) {
            this.list = res.result;
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    // 我的关注
    initAttention() {
      api_user_follow()
        .then(res => {
          if (res.code == 200) {
            this.userList = res.result;
            console.log(this.userList);
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    initArticle() {
      api_getUserAllMe_block()
        .then(res => {
          if (res.code == 200) {
            this.name = "我的文章";
            this.list = res.result;
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    handleAvatarSuccess(res, file) {
      this.form.head = res.result;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    // 初始化自己的文章内容
    this.initArticle();
    // 初始化获取自己的信息
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
