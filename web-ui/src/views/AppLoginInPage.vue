<template>
  <div class="login-container">
    <el-form ref="loginForm" class="login-form" @submit.native.prevent="login">
      <el-form-item label="账号" required>
        <el-input v-model="credentials.username" placeholder="Enter your username"></el-input>
      </el-form-item>
      <el-form-item label="密码" required>
        <el-input v-model="credentials.password" autocomplete="off" placeholder="Enter your password" type="password"></el-input>
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
    }
  }
};
</script>

<style scoped>
.login-container {
  height: calc(100vh - 16px); /* 减去大约的额外高度 */
  width: calc(100vw - 10px); /* 控制宽度以防横向滚动 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  overflow: hidden; /* 强制隐藏所有滚动条 */
  /* 添加线性渐变背景 */
  background: linear-gradient(to right, #cb7c44, #130a2d);
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
  background-color: #d99f2e;
}
</style>