<!-- 作者信息 -->
<template>
  <div class>
    <div class="box">
      <div class="icon">
        <span>作者信息</span>
      </div>
      <div>1</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  components: {},
  data() {
    return {};
  },
  props: ["id"],
  //计算属性
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {},
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    // 获取的是文章主键
    // 用于查询文章作者的信息
    console.log("组件里获取的id---" + this.id);
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
  width: 21.4rem;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.icon {
  border-width: 0 0 1px 0;
  border-style: solid;
  border-color: rgb(144, 147, 153, 0.1);
  font-size: 1.16rem;
  line-height: 1.29;
  color: #333;
}
.box > div {
  padding: 1.3rem 1.3rem;
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
</style>