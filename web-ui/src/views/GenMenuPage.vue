<template>
  <div>
    <GenCfgForm mode="search" :isInline="true" :textAlignVal="'right'" @form-submit="handleFormDataSubmit"></GenCfgForm>
    <el-button-group>
      <el-button size="mini" type="primary" @click="openGenCfgAddDialog"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</el-button>
      <el-button size="mini" type="primary" @click="openGenCfgUpdateDialog"><i class="fa fa-plus"></i>&nbsp;&nbsp;更新</el-button>
      <el-button size="mini" type="danger" @click="genCfgBatchDelete"><i class="fa fa-minus"></i>&nbsp;&nbsp;删除</el-button>
    </el-button-group>
    <GenCfgTable :initialData="tableData"></GenCfgTable>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageVo.pageNum"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageVo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageVo.total">
    </el-pagination>
    <el-dialog
        :title="genCfgAddUpdateDialogVo.title"
        :visible.sync="genCfgAddUpdateDialogVo.show"
        width="30%">
      <div>
        <GenCfgForm mode="add" :initialData="genCfgFormVo" @form-submit="handleFormDataSubmit" v-if="genCfgAddUpdateDialogVo.mode==='add'"></GenCfgForm>
        <GenCfgForm mode="update" :initialData="genCfgFormVo" @form-submit="handleFormDataSubmit" v-if="genCfgAddUpdateDialogVo.mode==='update'"></GenCfgForm>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import GenCfgForm from '@/views/GenCfgForm.vue';
import GenCfgTable from '@/views/GenCfgTable.vue';

export default {
  name: "GenMenuPage",
  components: {
    GenCfgForm, GenCfgTable
  },
  data() {
    return {
      genCfgFormVo: {
        id: null,
        userId: null,
        genCfgName: '',
        sourceCodeAbsPath: '',
        domainPackage: '',
        dtoPackage: '',
        mapperPackage: '',
        servicePackage: '',
        controllerPackage: '',
        easyExcelListenerPackage: '',
        resourceAbsPath: '',
        mapperXmlPath: '',
        isExtendTkMapper: '',
        tkMapperPackage: '',
        isGenSwagger: '',
        isGenComment: '',
        isGenEasyExcel: '',
        isGenJavaxValidation: '',
        createTime: '',
        updateTime: ''
      },
      tableData: [],
      pageVo: {
        pageNum: 1,
        pageSize: 10,
        total: 0
      },
      genCfgAddUpdateDialogVo: {
        show: false,
        title: '',
        mode: 'add',
      },
    }
  },
  methods: {
    handleFormDataSubmit(payload) {
      console.log('接受到组件传递的参数：', payload);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    openGenCfgAddDialog() {
      this.genCfgAddUpdateDialogVo.title = '新增配置';
      this.genCfgAddUpdateDialogVo.mode = 'add';
      this.genCfgAddUpdateDialogVo.show = true;
    },
    openGenCfgUpdateDialog() {
      this.genCfgAddUpdateDialogVo.title = '更新配置';
      this.genCfgAddUpdateDialogVo.mode = 'update';
      this.genCfgAddUpdateDialogVo.show = true;
    },
    genCfgBatchDelete() {
      console.log('批量删除');
    },
  },// methods
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