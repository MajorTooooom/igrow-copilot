<template>
  <div>
    <el-form size="mini" ref="queryVo" :inline="true" :model="queryVo" label-width="100px" style="text-align: right;" class="query-form-clazz">
      <el-form-item label="ID" style="margin-right: -40px;">
        <el-input v-model="queryVo.id"></el-input>
      </el-form-item>
      <el-form-item label="用户ID" style="margin-right: -25px;">
        <el-input v-model="queryVo.userId" style="width: 60px;" disabled></el-input>
      </el-form-item>
      <el-form-item label="配置名称" style="margin-right: -10px;">
        <el-input v-model="queryVo.cfgName"></el-input>
      </el-form-item>
      <el-form-item label="连接用户名" style="margin-right: -20px;">
        <el-input v-model="queryVo.userName"></el-input>
      </el-form-item>
      <el-form-item label="连接密码" style="margin-right: 5px;">
        <el-input v-model="queryVo.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="conditionalPagingQuery">查询</el-button>
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="openAddDialog"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</el-button>
    <el-button size="mini" type="danger" @click="batchDelete"><i class="fa fa-minus"></i>&nbsp;&nbsp;删除</el-button>
    <el-table
        size="mini"
        :fit="true"
        :stripe="true"
        :border="true"
        :row-style="getRowStyle"
        :cell-style="getCellStyle"
        :header-cell-style="getHeaderCellStyle"
        @selection-change="handleSelectionChange"
        :max-height="900"
        :data="tableVo.list"
        style="width: 100%;min-height:200px;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="ID" width="50" prop="id" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="用户ID" width="60" prop="userId" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="配置名称" width="200" prop="cfgName" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="URL" prop="url" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="连接用户名" width="200" prop="userName" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="连接密码" width="200" prop="password" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="创建时间" width="200" prop="createTime" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="更新时间" width="200" prop="updateTime" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="openUpdateDialog(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        style="text-align: center;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageVo.pageNum"
        :page-size="pageVo.pageSize"
        :page-sizes="[20,50,100, 200, 300, 400]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageVo.total">
    </el-pagination>
    <el-dialog :title="modelDialogVo.title" :visible.sync="modelDialogVo.show" width="30%">
      <el-form :model="modelDialogVo.modelVo">
        <el-form-item label="ID" v-if="modelDialogVo.type==='update'" style="margin-bottom: -5px;">
          <el-input v-model="modelDialogVo.modelVo.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户ID" style="margin-bottom: -5px;">
          <el-input v-model="modelDialogVo.modelVo.userId" disabled></el-input>
        </el-form-item>
        <el-form-item label="配置名称" style="margin-bottom: -5px;">
          <el-input v-model="modelDialogVo.modelVo.cfgName"></el-input>
        </el-form-item>
        <el-form-item label="URL" style="margin-bottom: -5px;">
          <el-input v-model="modelDialogVo.modelVo.url"></el-input>
        </el-form-item>
        <el-form-item label="连接用户名" style="margin-bottom: -5px;">
          <el-input v-model="modelDialogVo.modelVo.userName"></el-input>
        </el-form-item>
        <el-form-item label="连接密码" style="margin-bottom: -5px;">
          <el-input v-model="modelDialogVo.modelVo.password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitAdd" v-if="modelDialogVo.type==='add'">新增</el-button>
        <el-button @click="submitUpdate" v-if="modelDialogVo.type==='update'">更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import {addFn, deleteFn, pageFn, updateFn, rowStyleFn, cellStyleFn, headerCellStyleFn} from '@/api/connMenuApi';

export default {
  name: "ConnMenuPage",
  components: {},
  data() {
    return {
      queryVo: {
        userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID),
      },
      tableVo: {
        list: [],
        multipleSelection: [],
      },
      pageVo: {
        pageNum: 1,
        pageSize: 20,
        total: 0,
      },
      modelDialogVo: {
        title: '',
        type: 'add',// add/update
        modelVo: {},
        show: false,
      },
    }
  },
  methods: {
    conditionalPagingQuery() {
      pageFn(this.queryVo, this.pageVo).then(res => {
        this.$set(this.tableVo, 'multipleSelection', []);
        this.$set(this.tableVo, 'list', res.rows);
        this.$set(this.pageVo, 'total', res.total);
      });
    },
    getRowStyle: rowStyleFn,
    getCellStyle: cellStyleFn,
    getHeaderCellStyle: headerCellStyleFn,
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    submitAdd() {
      addFn(this.modelDialogVo.modelVo).then(res => {
        this.modelDialogVo.show = false;
        this.conditionalPagingQuery();
      });
    },
    submitUpdate() {
      updateFn(this.modelDialogVo.modelVo).then(res => {
        this.modelDialogVo.show = false;
        this.conditionalPagingQuery();
      });
    },
    openAddDialog() {
      this.modelDialogVo.title = '新增';
      this.modelDialogVo.type = 'add';
      this.modelDialogVo.modelVo = {userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID),};
      this.modelDialogVo.show = true;
    },
    openUpdateDialog(row) {
      this.modelDialogVo.title = '更新';
      this.modelDialogVo.type = 'update';
      this.modelDialogVo.modelVo = row;
      this.modelDialogVo.show = true;
    },
    handleSelectionChange(rows) {
      this.$set(this.tableVo, 'multipleSelection', rows);
    },
    batchDelete() {
      if (this.tableVo.multipleSelection.length <= 0) {
        Message.warning('请选择要删除的数据');
        return;
        return false;
      }
      // 收集ID
      const ids = this.tableVo.multipleSelection.map(item => item.id);
      MessageBox.confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteFn(ids).then(res => {
          this.conditionalPagingQuery();
        });
      }).catch(() => {
        Message.info('已取消删除');
      });
    },
  },// methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    this.$nextTick(() => {
      this.conditionalPagingQuery();
    });
  },
}
</script>

<style scoped>
</style>