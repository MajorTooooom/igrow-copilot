<template>
  <div class="template-body">
    <GenCfgForm mode="search" :isInline="true" :textAlignVal="'right'" @form-submit="handleFormDataSubmit"></GenCfgForm>
    <el-button-group style="margin-left: 1px;">
      <el-button size="mini" type="primary" @click="openGenCfgAddDialog"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</el-button>
      <el-button size="mini" type="danger" @click="genCfgBatchDelete"><i class="fa fa-minus"></i>&nbsp;&nbsp;删除</el-button>
    </el-button-group>
    <GenCfgTable ref="GenCfgTableRef" :initialData="tableData" @open-update="openGenCfgUpdateDialog"></GenCfgTable>
    <el-pagination
        style="text-align: center;"
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
        width="60%">
      <div>
        <GenCfgForm ref="GenCfgFormAddRef" mode="add" :initialData="genCfgFormVo" @form-submit="handleFormDataSubmit" v-if="genCfgAddUpdateDialogVo.mode==='add'" :textAlignVal="'left'"></GenCfgForm>
        <GenCfgForm ref="GenCfgFormUpdateRef" mode="update" :initialData="genCfgFormVo" @form-submit="handleFormDataSubmit" v-if="genCfgAddUpdateDialogVo.mode==='update'" :textAlignVal="'left'"></GenCfgForm>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="genCfgAddUpdateDialogVo.show = false">取 消</el-button>
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
import {
  addGenCfgFn,
  updateGenCfgFn,
  pageGenCfgFn,
  batchDeleteActionFn,
} from '@/api/genCfgApi';

export default {
  name: "GenMenuPage",
  components: {
    GenCfgForm, GenCfgTable
  },
  data() {
    return {
      genCfgFormVo: {
        id: null,
        userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID),
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
        total: 0,
        orderBy: 'update_time desc',
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
      // this.genCfgAddUpdateDialogVo.show = false;
      if (payload.mode === 'add') {
        this.addGenCfg(payload.formData);
      } else if (payload.mode === 'update') {
        this.updateGenCfg(payload.formData);
      } else if (payload.mode === 'search') {
        this.searchGenCfg(payload.formData);
      }
    },
    handleSizeChange(val) {
      this.pageVo.pageSize = val;
      this.searchGenCfg();
    },
    handleCurrentChange(val) {
      this.pageVo.pageNum = val;
      this.searchGenCfg();
    },
    openGenCfgAddDialog() {
      this.genCfgAddUpdateDialogVo.mode = 'add';
      this.genCfgAddUpdateDialogVo.title = '新增配置';
      this.$nextTick(() => {
        this.$refs.GenCfgFormAddRef.resetGenCfgForm();
      });
      this.genCfgAddUpdateDialogVo.show = true;
    },
    openGenCfgUpdateDialog(row) {
      this.genCfgAddUpdateDialogVo.title = '更新配置';
      this.genCfgAddUpdateDialogVo.mode = 'update';
      this.$nextTick(() => {
        this.$refs.GenCfgFormUpdateRef.justSetGenCfgFormVo(row);
      });
      this.genCfgAddUpdateDialogVo.show = true;
    },
    genCfgBatchDelete() {
      // 二次确认
      MessageBox.confirm('此操作将永久删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.batchDeleteAction();
      }).catch(() => {
        Message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    batchDeleteAction() {
      let ids = this.$refs.GenCfgTableRef.getMultipleSelectionIds();
      if (!(ids && ids.length > 0)) {
        Message({type: 'warning', message: '请选择要删除的配置'});
        return false;
      }
      // 执行删除操作
      batchDeleteActionFn(ids).then(res => {
        this.searchGenCfg({userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID)});
      });
    },
    addGenCfg(formData) {
      addGenCfgFn(formData).then(res => {
        this.genCfgAddUpdateDialogVo.show = false;
        this.searchGenCfg();
      });
    },
    updateGenCfg(formData) {
      updateGenCfgFn(formData).then(res => {
        this.genCfgAddUpdateDialogVo.show = false;
        this.searchGenCfg({userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID)});
      });
    },
    searchGenCfg(formData) {
      pageGenCfgFn(formData || {}, this.pageVo).then(res => {
        this.tableData = res.rows;
        this.pageVo.total = res.total;
      });
    },
  },// methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    this.searchGenCfg({userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID)});
  },
}
</script>

<style scoped>
.template-body {
  padding-top: 10px;
}
</style>