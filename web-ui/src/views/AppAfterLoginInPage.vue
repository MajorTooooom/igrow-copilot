<template>
  <div>
    <el-container v-cloak class="top-container-clazz">
      <!--Aside-->
      <el-aside width="180px">
        <div style="height: 50px;text-align: center;">
          <span style="position: relative;top: 10px;">igrow</span>
        </div>
        <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" @select="handleSelect">
          <el-menu-item index="1"><i class="fa fa-home"></i>&nbsp;&nbsp;首页</el-menu-item>
          <el-menu-item index="2"><i class="fa fa-database"></i>&nbsp;&nbsp;数据源配置</el-menu-item>
          <el-menu-item index="3"><i class="fa fa-table"></i>&nbsp;&nbsp;表&表字段配置</el-menu-item>
          <el-menu-item index="4"><i class="fa fa-plug"></i>&nbsp;&nbsp;渲染配置</el-menu-item>
          <el-menu-item index="5"><i class="fa fa-question-circle"></i>&nbsp;&nbsp;关于</el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="height: 50px;">Header</el-header>
        <!--Main-->
        <el-main style="padding: 5px !important;">
          <!--<keep-alive><router-view/></keep-alive>-->
          <router-view/>
        </el-main>
        <el-footer style="height: 50px;">Footer</el-footer>
      </el-container>
    </el-container>

    <div class="author-info-zone">
      <p>
        <span style="color:#4aa3fb;">© 2024 </span>
        <span style="color:#fc0000;">dororo. </span>
        <span style="color:#EB547D;">All </span>
        <span style="color:#9F6AA7;">rights </span>
        <span style="color:#5476B3;">reserved. </span>
      </p>
    </div>

  </div>
</template>

<script>
import 'font-awesome/css/font-awesome.min.css';
import * as CommonConsts from '@/config/CommonConsts';

export default {
  name: "AppAfterLoginInPage",
  data() {
    return {
      activeIndex: '1',
      pageLoadingVo: null,
    };
  },
  methods: {
    handleSelect(index, indexPath) {
      let arr = [
        {index: '1', path: '/'},
        {index: '2', path: '/conn-menu'},
        {index: '3', path: '/table-menu'},
        {index: '4', path: '/gen-menu'},
        {index: '5', path: '/about'},
      ];
      let path = arr.find(item => item.index === index).path;
      if (this.$route.path !== path) {
        this.$router.push(path);
      }
    },
    fullScreenOpen() {
      this.pageLoadingVo = this.$loading({
        lock: true,
        text: '加载中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },
    fullScreenClose() {
      try {
        this.pageLoadingVo.close();
      } catch (e) {
        console.error(e);
      }
    },
  },// methods
  mounted() {
    this.$bus.$on(CommonConsts.FULL_SCREEN_OPEN, this.fullScreenOpen);
    this.$bus.$on(CommonConsts.FULL_SCREEN_CLOSE, this.fullScreenClose);
  },
}
;
</script>

<style scoped>
[v-cloak] {
  display: none;
}

/* ===============================================[测试阶段颜色区分][start]======================================================== */
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
}

/* ===============================================[测试阶段颜色区分][end  ]======================================================== */


.top-container-clazz {
  overflow: hidden;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: 0px;
}


.author-info-zone {
  font-family: 'Rubik Mono One', sans-serif;
  position: fixed;
  right: 10px;
  bottom: -3px;
}
</style>