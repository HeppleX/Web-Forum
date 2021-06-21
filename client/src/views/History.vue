<!--  -->
<template>
  <div class>
    <div class="history">
      <div class="box_list hvr-underline-from-center" v-for="item in list">
        <div>
          <span style="color: #b71ed7;font-weight: 500;">{{name}}</span>
          <span class="meun">·</span>
          <span class="meuns">{{item.user.nickname}}</span>
          <span class="meun">·</span>
          <span class="meun">{{item.createDate}}</span>
          <span class="meun">·</span>
          <span class="meuns" v-if="item.block != null">{{item.block.bname}}</span>
          <span class="meun" v-else>其它</span>
        </div>
        <div style="display: flex;">
          <div style="width: 90%;">
            <div class="box_list_title" @click="toPath(item.id)">{{item.title}}</div>
            <div>
              <span class="meuns" style="margin-left: 0px;">热度:378</span>
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
import { api_get_history } from "../api/http.js";
export default {
  components: {},
  data() {
    return {
      list: [],
      current: 1,
      size: 10,
      name: "浏览记录"
    };
  },
  //计算属性
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    toPath(id) {
      this.$router.push("/show/article/" + id);
    },
    init() {
      api_get_history({
        current: this.current,
        size: this.size
      })
        .then(res => {
          if (res.code == 200) {
            this.list = res.result;
            console.log(this.list);
          } else if (res.code == 501) {
            this.$message("无历史记录");
          }
        })
        .catch(error => {
          this.$message.error("服务器错误!");
        });
    },
    addList() {
      api_get_history({
        current: this.current + 1,
        size: this.size
      })
        .then(res => {
          if (res.code == 200) {
            this.list = this.list.concat(res.result);
            this.current = this.current + 1;
          } else if (res.code == 501) {
            this.$message.success("已全部加载完毕!");
          }
        })
        .catch(error => {
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
<style scoped>
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
  width: 96.25%;
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

.history {
  width: 80rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  margin: auto;
}
</style>
