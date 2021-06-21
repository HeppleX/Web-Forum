<!-- 写文章页面 -->
<template>
  <div class="article">
    <div class="article_header">
      <input type="text" v-model="title" placeholder="请输入文章标题..." />
      <div style="display: inline;">
        <span class="item" style="margin-right: 15px;">
          <el-select
            style="margin-right: 15px;"
            size="small"
            v-model="value"
            filterable
            placeholder="选择发布的版块"
          >
            <el-option
              v-for="item in options"
              :key="item.bid"
              :label="item.bname"
              :value="item.bid"
            ></el-option>
          </el-select>
          <el-button size="small" type="primary" @click="saveArticle()">发布</el-button>
          <el-button size="small" type="warning" @click="reset()">重置</el-button>
        </span>
      </div>
    </div>
    <le-editor id="editor" @save="save" style="min-height:100%"></le-editor>
  </div>
</template>

<script>
import axios from "axios";
import { api_Open_Block, api_save_article } from "../api/http.js";
export default {
  name: "",
  components: {},
  data() {
    return {
      title: "",
      value: null,
      options: []
    };
  },
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    save: function(val) {
      // 获取预览文本
      console.log(val);
    },
    saveArticle() {
      // 这个组件需要手动操作dom节点
      var editor = document.getElementsByClassName("markdown-body")[0]
        .innerHTML;
      console.log(typeof editor);
      api_save_article({
        title: this.title,
        content: editor,
        bid: this.value
      })
        .then(res => {
          if (res.code == 200) {
            this.$message.success("发布成功!");
            this.title = "";
            this.value = null;
            this.$router.push("/index/new");
          } else {
            console.log(editor);
            this.$message.error(res.msg);
          }
        })
        .catch(error => {
          this.$message.error("服务器错误");
        });
    },
    init() {
      api_Open_Block().then(res => {
        console.log(res);
        if (res.code == 200) {
          this.options = res.result;
        }
      });
    },
    reset() {
      this.title = "";
      this.value = "";
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
<style lang="css" scoped>
/* @import url(); 引入公共css类 */
/*设置编辑器宽高*/

.article {
  margin-top: -19px;
  width: 100%;
  margin-left: auto;
  margin-right: auto;
  height: 82vh;
}
.article_header {
  height: 5rem;
  background-color: white;
  margin-bottom: 2px;
}
.article_header > input {
  width: 50rem;
  height: 100%;
  border: none;
  background-color: transparent;
  outline: none;
  border: none;
  margin-left: 15px;
  font-size: 25px;
  font-weight: bold;
}
input::-webkit-input-placeholder {
  font-size: 25px;
} /*webkit 内核浏览器*/
.item {
  float: right;
  line-height: 4.5rem;
}
#editor >>> .le-editor-tools {
  margin: auto;
}
#editor >>> .CodeMirror-sizer {
  background: beige;
  color: black;
}
#editor >>> .cm-s-base16-dark.CodeMirror {
  background: gainsboro;
}
#editor >>> .cm-s-base16-dark .CodeMirror-gutters {
  background: gainsboro;
}
</style>