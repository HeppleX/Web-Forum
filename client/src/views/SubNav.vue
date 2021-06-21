<!-- 文章列表 -->
<template>
  <div class style="overflow-y:hidden">
    <div class="box">
      <router-link class="no_link" to="/index/hot">热门</router-link>
      <router-link class="no_link" to="/index/new">最新</router-link>
      <router-link class="no_link" to="/index/attention">关注</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  components: {},
  data() {
    return {
      index: 0,
      count: 10
    };
  },
  //计算属性
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    setIndex(v) {
      this.index = v;
    },
    addList() {
      this.count = this.count + 10;
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {},
  watch: {},

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
  margin-right: 12px;
  text-decoration: none;
}
.link,
.no_link:hover {
  color: #007fff;
}
.box_list {
  padding: 1.3rem 1.5rem;
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
.router-link-active {
  color: #007fff;
}
</style>