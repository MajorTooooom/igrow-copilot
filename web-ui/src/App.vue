<template>
  <div id="app">
    <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" @select="handleSelect">
      <el-menu-item index="1">首页</el-menu-item>
      <el-menu-item index="2">关于</el-menu-item>
    </el-menu>
    <router-view/>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: '1'
    };
  },
  methods: {
    handleSelect(index, indexPath) {
      let path = '/';
      if (index === '2') {
        path = '/about';
      }
      if (this.$route.path !== path) {
        this.$router.push(path);
      }
    }
  },
};
</script>

<style>
.el-menu-vertical-demo {
  width: 200px;
}
</style>