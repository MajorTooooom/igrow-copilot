<template>
  <div>
    <h1>{{mode}}</h1>
    <el-form size="mini" ref="genCfgFormVo" :model="genCfgFormVo" label-width="180px" :inline="isInline" :style="{'textAlign':textAlignVal}">
      <el-form-item label="ID" v-if="mode === 'search' || mode === 'update'">
        <el-input v-model.number="genCfgFormVo.id" type="number"></el-input>
      </el-form-item>
      <el-form-item label="用户ID">
        <el-input v-model.number="genCfgFormVo.userId" type="number" disabled></el-input>
      </el-form-item>
      <el-form-item label="配置名称">
        <el-input v-model="genCfgFormVo.genCfgName"></el-input>
      </el-form-item>
      <el-form-item label="源代码路径">
        <el-input v-model="genCfgFormVo.sourceCodeAbsPath"></el-input>
      </el-form-item>
      <el-form-item label="域包名">
        <el-input v-model="genCfgFormVo.domainPackage"></el-input>
      </el-form-item>
      <el-form-item label="DTO包名">
        <el-input v-model="genCfgFormVo.dtoPackage"></el-input>
      </el-form-item>
      <el-form-item label="Mapper包名">
        <el-input v-model="genCfgFormVo.mapperPackage"></el-input>
      </el-form-item>
      <el-form-item label="服务包名">
        <el-input v-model="genCfgFormVo.servicePackage"></el-input>
      </el-form-item>
      <el-form-item label="控制器包名">
        <el-input v-model="genCfgFormVo.controllerPackage"></el-input>
      </el-form-item>
      <el-form-item label="EasyExcel监听器包名">
        <el-input v-model="genCfgFormVo.easyExcelListenerPackage"></el-input>
      </el-form-item>
      <el-form-item label="资源路径">
        <el-input v-model="genCfgFormVo.resourceAbsPath"></el-input>
      </el-form-item>
      <el-form-item label="Mapper XML路径">
        <el-input v-model="genCfgFormVo.mapperXmlPath"></el-input>
      </el-form-item>
      <el-form-item label="是否扩展TkMapper">
        <el-select v-model="genCfgFormVo.isExtendTkMapper" placeholder="请选择">
          <el-option label="是" value="true"></el-option>
          <el-option label="否" value="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="TkMapper包名">
        <el-input v-model="genCfgFormVo.tkMapperPackage"></el-input>
      </el-form-item>
      <el-form-item label="是否生成Swagger文档">
        <el-select v-model="genCfgFormVo.isGenSwagger" placeholder="请选择">
          <el-option label="是" value="true"></el-option>
          <el-option label="否" value="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否生成注释">
        <el-select v-model="genCfgFormVo.isGenComment" placeholder="请选择">
          <el-option label="是" value="true"></el-option>
          <el-option label="否" value="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否生成EasyExcel">
        <el-select v-model="genCfgFormVo.isGenEasyExcel" placeholder="请选择">
          <el-option label="是" value="true"></el-option>
          <el-option label="否" value="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否生成Javax校验">
        <el-select v-model="genCfgFormVo.isGenJavaxValidation" placeholder="请选择">
          <el-option label="是" value="true"></el-option>
          <el-option label="否" value="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" v-if="mode === 'search'">
        <el-date-picker
            v-model="genCfgFormVo.createTime"
            type="datetime"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间" v-if="mode === 'search'">
        <el-date-picker
            v-model="genCfgFormVo.updateTime"
            type="datetime"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">{{ buttonLabel }}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';

export default {
  name: "GenCfgForm",
  props: {
    mode: {
      type: String,
      default: 'add' // 默认模式为 'add'
    },
    initialData: {
      type: Object,
      default: () => ({
        userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID),
      })
    },
    isInline: {
      type: Boolean,
      default: false
    },
    textAlignVal: {
      type: String,
      default: 'center'
    }
  },
  data() {
    return {
      genCfgFormVo: this.initialData
    };
  },
  computed: {
    buttonLabel() {
      if (this.mode === 'add') {
        return '添加';
      } else if (this.mode === 'update') {
        return '更新';
      } else if (this.mode === 'search') {
        return '搜索';
      }
    }
  },
  methods: {
    handleSubmit() {
      let eventPayload = {
        formData: this.genCfgFormVo,
        mode: this.mode
      };
      this.$emit('form-submit', eventPayload);
    }
  }
};
</script>

<style scoped>
</style>