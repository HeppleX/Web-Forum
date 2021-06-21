<template>
  <div class="home">
    <div class="box">
      <div class="box-left">
        <!-- <ListArticle ></ListArticle> -->
        <!-- 二级路由 -->
        <router-view />
      </div>
      <div class="box-right">
        <div class="smate">
          <Author></Author>
          <!-- <br> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ListArticle from "./ListArticle.vue";
import Author from "./Author.vue";
export default {
  name: "home",
  components: {
    ListArticle,
    Author
  }
};
</script>
<style scoped="">
.box {
  margin: auto;
  max-width: 960px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}
.box-left {
  width: 57.85rem;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.box-right {
  width: 20.85rem;
}
.smate {
  position: fixed;
}
/********/
</style>
