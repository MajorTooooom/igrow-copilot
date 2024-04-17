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
import {FN_CHANGE_USER_ID, SESSION_USER_ID} from "@/config/CommonConsts";

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
  methods: {},
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
  },
};
</script>

<style scoped>
</style>