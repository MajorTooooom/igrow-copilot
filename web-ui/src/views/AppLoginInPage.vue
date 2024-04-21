<template>
  <div class="login-container">
    <el-form ref="loginForm" class="login-form" @submit.native.prevent="login">
      <h1 style="text-align: center;">IGrowGen</h1>
      <el-form-item label="" required>
        <el-input v-model="credentials.username" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item label="" required>
        <el-input v-model="credentials.password" placeholder="密码" autocomplete="off" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <div style="display: inline-block; width: 80%;">
          <el-button type="primary" @click="login"><i class="fa fa-user-o"></i>&nbsp;&nbsp;登陆</el-button>
        </div>
        <div style="display: inline-block;width: 20%;">
          <el-button type="success" @click="signUp">注册</el-button>
        </div>
      </el-form-item>
    </el-form>
    <div class="author-info-zone">
      <p>
        <span style="color:#d99f2e;">© 2024 </span>
        <span style="color:#020202;">dororo. </span>
        <span style="color:#001285;">All </span>
        <span style="color:#234611;">rights </span>
        <span style="color:#855600;">reserved. </span>
      </p>
    </div>
  </div>
</template>

<script>
import 'font-awesome/css/font-awesome.min.css';
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import {loginFn, signUpFn} from '@/api/loginInApi';
import * as CommonConsts from '@/config/CommonConsts';

export default {
  name: 'AppLoginInPage',
  data() {
    return {
      credentials: {
        username: '',
        password: ''
      }
    };
  },
  methods: {
    login() {
      loginFn(this.credentials).then(response => {
        sessionStorage.setItem(CommonConsts.SESSION_USER_ID, response.data);
        this.$bus.$emit(CommonConsts.FN_CHANGE_USER_ID, response.data);
      });
    },
    signUp() {
      signUpFn(this.credentials).then(response => {
        console.log(response);
      });
    },
    handleKeyDown(event) {
      if (event.key === 'Enter') {
        console.log('Enter键被按下');
        this.login();
      }
    },
  },// methods
  mounted() {
    window.addEventListener('keydown', this.handleKeyDown);
  },// mounted
  beforeDestroy() {
    window.removeEventListener('keydown', this.handleKeyDown);
  },// beforeDestroy
};
</script>

<style scoped>
.login-container {
  overflow: hidden;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: 0px;
  font-family: 'Rubik Mono One', sans-serif;
  background: linear-gradient(to right, #00857d, #f50771);
  /* Flexbox for centering */
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.login-form {
  padding: 20px;
  background: white;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 300px;
  box-sizing: border-box;
}

.form-group {
  margin-bottom: 15px;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  border: none;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #00857d;
}

.author-info-zone {
  font-family: 'Rubik Mono One', sans-serif;
  position: fixed;
  right: 10px;
  bottom: -3px;
}
</style>