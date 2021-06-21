<!-- 版块页面 -->
<template>
  <div class="block">
    <div class="box">
      <div class="title hvr-buzz">全部版块</div>
      <div class="list">
        <Waterfall
          style="background-color: #FFFFFF;"
          :list="list"
          :gutter="40"
          :width="180"
          :gridWidth="gridWidth"
          :breakpoints="{
						1200: {
							//当屏幕宽度小于等于1200
							rowPerView: 4
						},
						800: {
							//当屏幕宽度小于等于800
							rowPerView: 3
						},
						500: {
							//当屏幕宽度小于等于500
							rowPerView: 2
						}
					}"
          animationDelay="0s"
          backgroundColor="rgb(73, 74, 95)"
          ref="waterfall"
        >
          <template slot="item" slot-scope="props">
            <div class="card hvr-float-shadow">
              <div @click="toPath(props.data.bid)" style="display: flex;cursor: pointer;">
                <img
                  :src="props.data.head"
                  style="width:70px; height:70px; border-radius: 5px"
                  @load="$refs.waterfall.refresh()"
                />
                <div>
                  <p class="list_title">{{props.data.bname}}</p>
                  <p style="margin:0 10px;color: #8a9aa9;">
                    3210
                    <span>关注</span>
                  </p>
                  <p style="margin:0 10px;color: #8a9aa9;">
                    151
                    <span>文章</span>
                  </p>
                </div>
              </div>
            </div>
          </template>
        </Waterfall>
      </div>
    </div>
  </div>
</template>

<script>
import Waterfall from "vue-waterfall-plugin";
import { api_Open_Block } from "../api/http.js";
export default {
  name: "",
  components: {
    Waterfall
  },
  data() {
    return {
      list: [],
      gridWidth: -1
    };
  },
  //计算属性
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    toPath(id) {
      this.$router.push({ path: "/details/" + id });
    },
    init() {
      api_Open_Block()
        .then(res => {
          if (res.code == 200) {
            this.list = res.result;
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
  margin: auto;
  max-width: 960px;
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border: 1px solid white;
  margin-bottom: 5rem;
}

.title {
  padding: 0 1.2rem;
  text-align: left;
  font-size: 2rem;
  color: #0e0e0e;
  font-weight: 600;
  margin: 2rem 0 0 2rem;
  cursor: pointer;
}

/* .list {
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
	} */

/* .el-image{
	border-radius: 15px;
} */
/* .item_title{
	display: inline;border: 1px solid;
	font-weight: 600;
	font-size: 1.4rem;
	justify-content: center;
	text-decoration: none;
	cursor: pointer;
} */
.list_title {
  width: 8rem;
  margin: 0 10px;
  font-weight: 600;
  color: #2e3135;
  font-size: 1.4rem;
  cursor: pointer;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.list_title:hover {
  color: #007fff;
}
</style>
