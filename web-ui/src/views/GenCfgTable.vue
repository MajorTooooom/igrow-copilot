<template>
  <div>
    <el-table
        size="mini"
        :fit="true"
        :stripe="true"
        :border="true"
        :row-style="genCfgRowStyle"
        :cell-style="genCfgCellStyle"
        :header-cell-style="genCfgHeaderCellStyle"
        :max-height="350"
        :data="tableData"
        style="width: 100%;min-height:200px;">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="userId" label="用户ID" width="100"></el-table-column>
      <el-table-column prop="genCfgName" label="配置名称" width="180"></el-table-column>
      <el-table-column prop="sourceCodeAbsPath" label="源代码路径" width="220"></el-table-column>
      <el-table-column prop="domainPackage" label="域包名" width="200"></el-table-column>
      <el-table-column prop="dtoPackage" label="DTO包名" width="200"></el-table-column>
      <el-table-column prop="mapperPackage" label="Mapper包名" width="200"></el-table-column>
      <el-table-column prop="servicePackage" label="服务包名" width="200"></el-table-column>
      <el-table-column prop="controllerPackage" label="控制器包名" width="200"></el-table-column>
      <el-table-column prop="easyExcelListenerPackage" label="EasyExcel监听器包名" width="250"></el-table-column>
      <el-table-column prop="resourceAbsPath" label="资源路径" width="220"></el-table-column>
      <el-table-column prop="mapperXmlPath" label="Mapper XML路径" width="220"></el-table-column>
      <el-table-column prop="isExtendTkMapper" label="是否扩展TkMapper" width="160"></el-table-column>
      <el-table-column prop="tkMapperPackage" label="TkMapper包名" width="200"></el-table-column>
      <el-table-column prop="isGenSwagger" label="是否生成Swagger文档" width="200"></el-table-column>
      <el-table-column prop="isGenComment" label="是否生成注释" width="160"></el-table-column>
      <el-table-column prop="isGenEasyExcel" label="是否生成EasyExcel" width="180"></el-table-column>
      <el-table-column prop="isGenJavaxValidation" label="是否生成Javax校验" width="200"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import {genCfgRowStyleFn, genCfgCellStyleFn, genCfgHeaderCellStyleFn} from '@/api/genCfgApi';

export default {
  name: "GenCfgTable",
  components: {},
  props: {
    initialData: {
      type: Array,
      default: () => ([])
    }
  },
  data() {
    return {
      tableData: this.initialData
    }
  },
  methods: {
    genCfgRowStyle: genCfgRowStyleFn,
    genCfgCellStyle: genCfgCellStyleFn,
    genCfgHeaderCellStyle: genCfgHeaderCellStyleFn,
  },
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    // this.init();
  },
}
</script>

<style scoped lang="scss">
</style>