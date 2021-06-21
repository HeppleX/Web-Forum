<template>
  <div id="app">
    <Header></Header>
    <router-view />
    <!-- 回到顶部 -->
    <el-backtop :visibility-height="100"></el-backtop>
  </div>
</template>
<script>
import Header from "./views/Header.vue";
export default {
  name: "Home",
  components: {
    Header
  }
};
</script>
<style scoped>
</style>
