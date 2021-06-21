<!-- 作者榜单 -->
<template>
  <div class>
    <div class="box">
      <div class="icon">
        <span>推荐作者</span>
        <span style="margin-left:9rem;">
          <i class="el-icon-refresh"></i>
          换一批
        </span>
      </div>
      <!-- 用户列表 -->
      <div class="example hvr-underline-from-center" v-for="item in list">
        <div>
          <el-avatar size="large" :src="item.head"></el-avatar>
        </div>
        <div style="margin-left: 5px;" @click="toUserInfo(item.uid)">
          <div>
            <span class="name">{{item.nickname}}</span>
            <span
              style="background-color:aquamarine; color: white;padding:0 6px;font-family:'幼圆';margin-left: 6px;"
            >LV{{item.score/10}}</span>
          </div>
          <div>
            <p class="name brief" style="width: 15rem;">{{item.uintroduce}}</p>
          </div>
        </div>
      </div>
      <div>
        <span class="link" style="margin:auto;">
          完整榜单
          <i class="el-icon-arrow-right"></i>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { api_Open_Author_There } from "../api/http.js";
export default {
  name: "",
  components: {},
  data() {
    return {
      list: []
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
    init() {
      api_Open_Author_There()
        .then(res => {
          if (res.code == 200) {
            this.list = res.result;
            console.log(this.list);
          }
        })
        .catch(errpr => {
          this.$message.error("服务器错误");
        });
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
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
<style  scoped>
/* @import url(); 引入公共css类 */
p {
  font-size: 14px;
}
.box {
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.icon {
  border-width: 0 0 1px 0;
  border-style: solid;
  border-color: rgb(144, 147, 153, 0.1);
  font-size: 1.16rem;
  line-height: 1.29;
  color: #71777c;
}
.box > div {
  padding: 1.1rem 1.1rem;
  display: flex;
  align-items: center;
}
.example {
  cursor: pointer;
  display: flex;
}
.example:hover {
  background-color: rgb(113, 119, 124, 0.05);
}
.name {
  color: SlateGray;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-bottom: 3px;
  overflow: hidden;
  width: 15rem;
}
.brief {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-top: 0px;
}
.link {
  color: #007fff;
  cursor: pointer;
  font-size: 1.16rem;
}
#block {
  margin-top: 3px;
  float: left;
  margin-left: 8px;
  width: 12px;
  height: 12px;
  background: #7fee1d;
  -moz-border-radius: 60px;
  -webkit-border-radius: 60px;
  border-radius: 60px;
}
</style>