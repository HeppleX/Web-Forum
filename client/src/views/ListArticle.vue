<!-- 文章列表 -->
<template>
  <div class style="overflow-y:hidden; border-radius: 5px">
    <SubNav></SubNav>
    <!--  无限循环 -->
    <div>
      <div class="box_list hvr-underline-from-center" v-for="item in list">
        <div>
          <span style="color: #b71ed7;font-weight: 500;">{{name}}</span>
          <span class="meun">·</span>
          <span class="meuns" @click="toUserInfo(item.user.uid)">{{item.user.nickname}}</span>
          <span class="meun">·</span>
          <span class="meun">{{item.createDate}}</span>
          <span class="meun">·</span>
          <span
            class="meuns"
            v-if="item.block != null"
            @click="toBlockInfo(item.block.bid)"
          >{{item.block.bname}}</span>
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
      <div class="box_list_add" style="text-align: center;">
        <span class="add_list" @click="addList()">
          加载更多
          <i class="el-icon-arrow-right"></i>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import {
  api_Open_article_hot,
  api_Open_article_new,
  api_Open_article_follow
} from "../api/http.js";
import SubNav from "./SubNav.vue";
export default {
  name: "",
  components: {
    SubNav
  },
  data() {
    return {
      index: 0,
      count: 15,
      list: [],
      current: 1,
      size: 10,
      flag: "",
      name: ""
    };
  },
  //计算属性
  computed: {},
  //监控data中的数据变化
  watch: {
    "$route.path": function(newVal, oldVal) {
      // 跳转至路由
      if (newVal == "/index/new") {
        this.name = "最新";
        this.current = 1;
        this.newInit();
      } else if (newVal == "/index/hot") {
        this.name = "最热";
        this.current = 1;
        this.hotInit();
      } else if (newVal == "/index/attention") {
        this.name = "关注";
        this.current = 1;
        this.followInit();
      }
    }
  },
  //方法集合
  methods: {
    toUserInfo(id) {
      this.$router.push("/user/info/" + id);
    },
    toBlockInfo(id) {
      this.$router.push("/details/" + id);
    },
    toPath(id) {
      this.$router.push("/show/article/" + id);
    },
    addList() {
      // 热度排序
      if (this.$route.path == "/index/hot") {
        api_Open_article_hot({
          current: this.current + 1,
          size: this.size
        })
          .then(res => {
            if (res.result.list.length != 0) {
              console.log(res.result.list);
              this.list = this.list.concat(res.result.list);
              // this.list.push.apply(this.list,b);
              this.current = this.current + 1;
            } else {
              this.$message.success("已全部加载完毕!");
            }
          })
          .catch(error => {
            this.$message.error("服务器错误");
          });
      }
      // 时间排序
      if (this.$route.path == "/index/new") {
        api_Open_article_new({
          current: this.current + 1,
          size: this.size
        })
          .then(res => {
            if (res.result.list.length != 0) {
              console.log(res.result.list);
              this.list = this.list.concat(res.result.list);
              // this.list.push.apply(this.list,b);
              this.current = this.current + 1;
              console.log(this.list);
            } else {
              this.$message.success("已全部加载完毕!");
            }
          })
          .catch(error => {
            this.$message.error("服务器错误");
          });
      }
      // 关注排序
      if (this.$route.path == "/index/attention") {
        api_Open_article_new({
          current: this.current + 1,
          size: this.size
        })
          .then(res => {
            if (res.result.list.length != 0) {
              console.log(res.result.list);
              this.list = this.list.concat(res.result.list);
              // this.list.push.apply(this.list,b);
              this.current = this.current + 1;
              console.log(this.list);
            } else {
              this.$message.success("已全部加载完毕!");
            }
          })
          .catch(error => {
            this.$message.error("服务器错误");
          });
      }
      if (this.$route.path == "/index/hot") {
        console.log("组件初始");
      }
    },

    newInit() {
      api_Open_article_new({
        current: this.current,
        size: this.size
      })
        .then(res => {
          if (res.code == 200) {
            this.list = res.result.list;
            // console.log(this.list)
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    hotInit() {
      api_Open_article_hot({
        current: this.current,
        size: this.size
      })
        .then(res => {
          if (res.code == 200) {
            this.list = res.result.list;
            // console.log(this.list)
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    followInit() {
      api_Open_article_follow()
        .then(res => {
          if (res.code == 200) {
            // console.log(res.result)
            this.list = res.result;
            // console.log(this.list)
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    if (this.$route.path == "/index/hot") {
      this.current = 1;
      this.name = "最热";
      this.hotInit();
    }
    if (this.$route.path == "/index/new") {
      this.name = "最新";
      this.current = 1;
      this.newInit();
    }
    if (this.$route.path == "/index/attention") {
      this.name = "关注";
      this.current = 1;
      this.followInit();
    }
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
<style  scoped>
/* @import url(); 引入公共css类 */
.box {
  padding: 1.3rem 1.5rem;
  border-bottom: 1px solid hsla(0, 0%, 59.2%, 0.1);
  line-height: normal;
  background-color: white;
  justify-content: flex-start;
  border-width: 0 0 1px 0;
  border-style: solid;
  border-color: rgb(144, 147, 153, 0.1);
}
/* 下边框线 透明度 */
.wire {
  border-width: 0 0 1px 0;
  border-style: solid;
  border-color: rgb(144, 147, 153, 0.3);
}
.box > span {
  margin-left: 8px;
}
/* 选中的样式 */
.link {
  color: #007fff;
  cursor: pointer;
  font-size: 1.16rem;
}
/* 没有被选中的央视 */
.no_link {
  color: #71777c;
  cursor: pointer;
  font-size: 1.16rem;
}
.link,
.no_link:hover {
  color: #007fff;
}
.box_list {
  padding: 1.1rem 1.5rem;
  width: 94.75%;
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
</style>