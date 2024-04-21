<template>
  <div id="app">
    <div v-if="loginInSuccess">
      <AppAfterLoginInPage/>
    </div>
    <div v-else>
      <AppLoginInPage/>
    </div>
  </div>
</template>

<script>
import AppLoginInPage from '@/views/AppLoginInPage';
import AppAfterLoginInPage from '@/views/AppAfterLoginInPage.vue';
import * as CommonConsts from '@/config/CommonConsts';
import {FN_CHANGE_USER_ID, FN_LOGIN_IN, SESSION_USER_ID} from "@/config/CommonConsts";

export default {
  components: {
    AppLoginInPage,
    AppAfterLoginInPage,
  },
  data() {
    return {
      loginInSuccess: false,
    };
  },
  methods: {
    handleKeyDown(event) {
      if (event.ctrlKey && event.key === 's') {
        event.preventDefault(); // 阻止默认的浏览器行为
        console.log('Ctrl+S被按下');
        // 在此处执行特定的操作，例如保存内容
      }
    },
  },// methods
  mounted() {
    let userId = sessionStorage.getItem(CommonConsts.SESSION_USER_ID);
    if (userId) {
      this.loginInSuccess = true;
    }
    this.$bus.$on(CommonConsts.FN_CHANGE_USER_ID, (data) => {
      if (data) {
        this.loginInSuccess = true;
      }
    });
    window.addEventListener('keydown', this.handleKeyDown);
  },// mounted
};
</script>

<style scoped>
</style>