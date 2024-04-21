<template>
  <div>
    <div class="avatar" style="font-size: 10px;" @click="userInfoDialogVo.show = true">
      <el-badge :value="99" class="item">
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
      </el-badge>
    </div>

    <el-dialog title="修改用户信息" :visible.sync="userInfoDialogVo.show">
      <el-form :model="genSysUserVo" label-width="80px">
        <el-form-item label="ID">
          <el-input v-model="genSysUserVo.id" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="genSysUserVo.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="genSysUserVo.password" type="password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userInfoDialogVo.show = false">取 消</el-button>
        <el-button type="primary" @click="updateGenSysUserVo">更 新</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import {getGenSysUserVoFn, updateGenSysUserVo, updateGenSysUserVoFn} from '@/api/Header';

export default {
  name: "Header",
  components: {},
  data() {
    return {
      userInfoDialogVo: {
        show: false,

      },
      genSysUserVo: {},
    }
  },
  methods: {
    setGenSysUserVo() {
      getGenSysUserVoFn({id: sessionStorage.getItem(CommonConsts.SESSION_USER_ID)}).then(response => {
        this.genSysUserVo = response.data;
      });
    },
    updateGenSysUserVo() {
      updateGenSysUserVoFn(this.genSysUserVo).then(response => {
        this.userInfoDialogVo.show = false;
      });
    },
  },// methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    this.$nextTick(() => {
      this.setGenSysUserVo();
    });
  },
}
</script>

<style scoped>
.avatar {
  position: fixed;
  right: 25px;
  top: 9px;
}
</style>