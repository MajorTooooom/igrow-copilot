<template>
  <div>
    <el-form size="mini" ref="tableCfgSearchFormVo" :model="tableCfgSearchFormVo" label-width="100px" :inline="true" style="text-align: right;">
      <el-form-item label="ID">
        <el-input v-model="tableCfgSearchFormVo.id"></el-input>
      </el-form-item>
      <el-form-item label="用户ID">
        <el-input v-model="tableCfgSearchFormVo.userId" disabled></el-input>
      </el-form-item>
      <el-form-item label="配置名称">
        <el-input v-model="tableCfgSearchFormVo.tableCfgName"></el-input>
      </el-form-item>
      <el-form-item label="数据源URL">
        <el-input v-model="tableCfgSearchFormVo.connUrl"></el-input>
      </el-form-item>
      <el-form-item label="数据源账户">
        <el-input v-model="tableCfgSearchFormVo.connUserName"></el-input>
      </el-form-item>
      <el-form-item label="数据源密码">
        <el-input v-model="tableCfgSearchFormVo.connPassword"></el-input>
      </el-form-item>
      <el-form-item label="库名">
        <el-input v-model="tableCfgSearchFormVo.tableSchema"></el-input>
      </el-form-item>
      <el-form-item label="表名">
        <el-input v-model="tableCfgSearchFormVo.tableName"></el-input>
      </el-form-item>
      <el-form-item label="实体类名">
        <el-input v-model="tableCfgSearchFormVo.domainName"></el-input>
      </el-form-item>
      <el-form-item label="实体类中文名">
        <el-input v-model="tableCfgSearchFormVo.domainZnName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getTableCfgPage">查询</el-button>
      </el-form-item>
    </el-form>
    <el-button-group>
      <el-button size="mini" type="primary" @click="openConnCfgDialog"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</el-button>
      <el-button size="mini" type="danger" @click="tableCfgBatchDelete"><i class="fa fa-minus"></i>&nbsp;&nbsp;删除</el-button>
    </el-button-group>
    <el-table
        size="mini"
        :fit="true"
        :stripe="true"
        :border="true"
        :row-style="tableCfgRowStyle"
        :cell-style="tableCfgCellStyle"
        :header-cell-style="tableCfgHeaderCellStyle"
        :max-height="350"
        :data="tableCfgTableVo.tableData"
        @selection-change="handleTableCfgSelectionChange"
        style="width: 100%;min-height:200px;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="ID" width="60" prop="id" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="用户ID" width="60" prop="userId" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="配置名称" width="200" prop="tableCfgName" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="数据源地址" prop="connUrl" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="数据源登录名" width="100" prop="connUserName" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="数据源密码" width="100" prop="connPassword" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="库名" width="100" prop="tableSchema" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="表名" width="100" prop="tableName" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="实体类名" width="100" prop="domainName" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="实体类中文名" width="100" prop="domainZnName" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="创建时间" width="100" prop="createTime" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="更新时间" width="100" prop="updateTime" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="openUpdateDialog(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleTableCfgPageSizeChange"
        @current-change="handleTableCfgPageNumChange"
        :current-page="tableCfgPaginationVo.pageNum"
        :page-sizes="[20,100, 200, 300, 400]"
        :page-size="tableCfgPaginationVo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableCfgPaginationVo.total">
    </el-pagination>
    <el-dialog
        title="请选择数据源"
        :visible.sync="connCfgDialogVo.show"
        :custom-class="'conn-cfg-dialog-body'"
        width="60%">
      <div>
        <el-form size="mini" ref="connCfgDialogVoForm" :model="connCfgDialogVo.form" label-width="80px">
          <el-form-item label="数据源">
            <el-select v-model="connCfgDialogVo.form.connCfgId" placeholder="请选择数据源" clearable filterable style="min-width: 100%;">
              <el-option v-for="item in connCfgDialogVo.connCfgOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="查询库表">
            <el-tooltip class="item" effect="dark" content="首次查询依然需要数据库握手耗时" placement="top" style="width: 150px">
              <el-button @click="getTableOptions(false)">缓存查询（默认）</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="直接查最新的库表信息并更新缓存" placement="top">
              <el-button type="success" plain @click="getTableOptions(true)">刷新查询</el-button>
            </el-tooltip>
          </el-form-item>
          <el-form-item label="库表">
            <el-cascader v-model="connCfgDialogVo.form.tableCfgChosen" :options="connCfgDialogVo.tableCfgOptions" clearable filterable placeholder="请选择库表" style="min-width: 100%;">
              <template slot-scope="{ node, data }">
                <span>{{ data.label }}</span>
                <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
              </template>
            </el-cascader>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableAndColumnFromDs">查询“表”信息以及“字段”信息</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--<span slot="footer" class="dialog-footer"><el-button @click="connCfgDialogVo.show = false">取 消</el-button></span>-->
    </el-dialog>
    <el-dialog
        :top="'10vh'"
        :title="addOrUpdateDialogVo.title"
        :visible.sync="addOrUpdateDialogVo.show"
        :custom-class="'add-or-update-dialog-body'"
        width="98%">
      <div>
        <el-form size="mini" ref="addOrUpdateDialogVoRowFormVo" :model="addOrUpdateDialogVo.rowFormVo" label-width="100px" :inline="true" style="text-align: right;">
          <el-form-item label="ID" v-if="addOrUpdateDialogVo.type==='update'">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.id"></el-input>
          </el-form-item>
          <el-form-item label="用户ID">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.userId" disabled style="max-width: 60px;"></el-input>
          </el-form-item>
          <el-form-item label="配置名称">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.tableCfgName" style="min-width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="数据源URL">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.connUrl"></el-input>
          </el-form-item>
          <el-form-item label="数据源账户">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.connUserName"></el-input>
          </el-form-item>
          <el-form-item label="数据源密码">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.connPassword"></el-input>
          </el-form-item>
          <el-form-item label="库名">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.tableSchema"></el-input>
          </el-form-item>
          <el-form-item label="表名">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.tableName"></el-input>
          </el-form-item>
          <el-form-item label="实体类名">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.domainName"></el-input>
          </el-form-item>
          <el-form-item label="实体类中文名">
            <el-input v-model="addOrUpdateDialogVo.rowFormVo.domainZnName"></el-input>
          </el-form-item>
        </el-form>
        <el-table
            size="mini"
            :fit="true"
            :stripe="true"
            :border="true"
            :row-style="columnCfgRowStyle"
            :cell-style="columnCfgCellStyle"
            :header-cell-style="columnCfgHeaderCellStyle"
            :max-height="350"
            :data="addOrUpdateDialogVo.columnTableData"
            style="width: 100%;min-height:200px;">
          <el-table-column label="ID" width="60" prop="id" v-if="addOrUpdateDialogVo.type==='update'" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column label="排序值" width="60" prop="ordinalPosition" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column label="列名" width="200" prop="columnName" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column label="列类型" width="100" prop="columnType" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column label="" width="100" prop="columnKey" :show-overflow-tooltip="true">
            <template slot="header" slot-scope="scope">
              <el-tooltip class="item" effect="dark" content="PRI表示主键，UNI表示唯一键，MUL表示可被索引的" placement="top">
                <span><i class="fa fa-question-circle-o"></i>&nbsp;键类型</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="Java字段名" width="200" prop="javaName" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.javaName" placeholder="请输入Java字段名"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="Java字段类型" width="100" prop="javaType" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.javaType" placeholder="请输入Java字段类型"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="注释" prop="columnComment" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.columnComment" placeholder="请输入注释"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="Swagger注释" prop="columnSwaggerComment" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.columnSwaggerComment" placeholder="请输入Swagger注释"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="validation注释" prop="columnValidationComment" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.columnValidationComment" placeholder="请输入validation注释"></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOrUpdateDialogVo.show = false">取 消</el-button>
        <el-button type="primary" @click="addTableCfg" v-if="addOrUpdateDialogVo.type==='add'">新 增</el-button>
        <el-button type="primary" @click="updateTableCfg" v-if="addOrUpdateDialogVo.type==='update'">更 新</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import {getConnCfgOptionsFn} from '@/api/connMenuApi';
import {
  getTableCfgPageFn,
  tableCfgRowStyleFn,
  tableCfgCellStyleFn,
  tableCfgHeaderCellStyleFn,
  getTableOptionsFn,
  getTableAndColumnFromDsFn,
  columnCfgRowStyleFn,
  columnCfgCellStyleFn,
  columnCfgHeaderCellStyleFn,
  addTableCfgFn,
  getColumnCfgByTableCfgFn,
  updateTableCfgFn,
} from '@/api/tableMenuApi';

export default {
  name: "TableMenuPage",
  components: {},
  data() {
    return {
      tableCfgSearchFormVo: {},
      tableCfgTableVo: {
        tableData: [],
        multipleSelection: [],
      },
      tableCfgPaginationVo: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
      },
      connCfgDialogVo: {
        show: false,
        connCfgOptions: [],
        tableCfgOptions: [],
        form: {
          connCfgId: null,
          tableCfgChosen: null,
        },
      },
      addOrUpdateDialogVo: {
        title: '',
        rowFormVo: {},
        columnTableData: [],
        show: false,
        type: 'add',// add or update
      },
    }
  },
  methods: {
    openUpdateDialog(row) {
      getColumnCfgByTableCfgFn(row).then(response => {
        this.addOrUpdateDialogVo.title = '编辑“表”信息&“表字段”信息配置';
        this.addOrUpdateDialogVo.rowFormVo = row;
        this.addOrUpdateDialogVo.columnTableData = response.data;
        this.addOrUpdateDialogVo.type = 'update';
        this.addOrUpdateDialogVo.show = true;
      });
    },
    getTableCfgPage() {
      getTableCfgPageFn(this.tableCfgSearchFormVo, this.tableCfgPaginationVo).then(res => {
        this.tableCfgTableVo.tableData = res.rows;
        this.tableCfgPaginationVo.total = res.total;
      });
    },
    tableCfgRowStyle: tableCfgRowStyleFn,
    tableCfgCellStyle: tableCfgCellStyleFn,
    tableCfgHeaderCellStyle: tableCfgHeaderCellStyleFn,
    handleTableCfgPageSizeChange(val) {
      this.tableCfgPaginationVo.pageSize = val;
      this.getTableCfgPage();
    },
    handleTableCfgPageNumChange(val) {
      this.tableCfgPaginationVo.pageNum = val;
      this.getTableCfgPage();
    },
    handleTableCfgSelectionChange(val) {
      this.tableCfgTableVo.multipleSelection = val;
      console.log(val);
    },
    openConnCfgDialog() {
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      getConnCfgOptionsFn().then(res => {
        this.connCfgDialogVo.connCfgOptions = res.data;
        this.connCfgDialogVo.show = true;
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    getTableOptions(forced) {
      // 根据选择ID在connCfgOptions中将数据定位出来
      let connCfgOption = this.connCfgDialogVo.connCfgOptions.find(item => item.value === this.connCfgDialogVo.form.connCfgId);
      if (!(connCfgOption && connCfgOption.data)) {
        Message.error('请选择数据源');
        return false;
      }
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      getTableOptionsFn(connCfgOption.data, {forced: forced}).then(response => {
        this.connCfgDialogVo.tableCfgOptions = response.data;
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      })
      ;
    },
    tableCfgBatchDelete() {
    },
    getTableAndColumnFromDs() {
      if (!this.connCfgDialogVo.form.connCfgId) {
        Message.error('请选择数据源');
        return false;
      }
      if (!(this.connCfgDialogVo.form.tableCfgChosen && this.connCfgDialogVo.form.tableCfgChosen.length > 0)) {
        Message.error('请选择库表');
        return false;
      }
      // let connCfg = this.connCfgOptions.find(item => item.value === this.connCfgForm.connCfgId).data;
      let connCfg = this.connCfgDialogVo.connCfgOptions.find(item => item.value === this.connCfgDialogVo.form.connCfgId).data;
      let payload = {
        url: connCfg.url,
        userName: connCfg.userName,
        password: connCfg.password,
        tableSchema: this.connCfgDialogVo.form.tableCfgChosen[0],
        tableName: this.connCfgDialogVo.form.tableCfgChosen[1],
      };
      //
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      getTableAndColumnFromDsFn(payload).then(response => {
        this.addOrUpdateDialogVo.title = '新增“表”信息&“表字段”信息配置';
        this.addOrUpdateDialogVo.rowFormVo = {
          userId: sessionStorage.getItem(CommonConsts.SESSION_USER_ID),
          tableCfgName: response.data.recommendedTableCfgName,
          connUrl: connCfg.url,
          connUserName: connCfg.userName,
          connPassword: connCfg.password,
          tableSchema: this.connCfgDialogVo.form.tableCfgChosen[0],
          tableName: this.connCfgDialogVo.form.tableCfgChosen[1],
          domainName: response.data.domainName,
          domainZnName: response.data.domainZnName,
        };
        this.addOrUpdateDialogVo.columnTableData = response.data.columns;
        this.addOrUpdateDialogVo.type = 'add';
        //
        this.connCfgDialogVo.show = false;
        this.addOrUpdateDialogVo.show = true;
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    columnCfgRowStyle: columnCfgRowStyleFn,
    columnCfgCellStyle: columnCfgCellStyleFn,
    columnCfgHeaderCellStyle: columnCfgHeaderCellStyleFn,
    addTableCfg() {
      let payload = this.addOrUpdateDialogVo.rowFormVo;
      payload.columnCfgs = this.addOrUpdateDialogVo.columnTableData;
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      addTableCfgFn(payload).then(response => {
        this.addOrUpdateDialogVo.show = false;
        this.getTableCfgPage();
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    updateTableCfg() {
      let payload = this.addOrUpdateDialogVo.rowFormVo;
      payload.columnCfgs = this.addOrUpdateDialogVo.columnTableData;
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      updateTableCfgFn(payload).then(response => {
        this.addOrUpdateDialogVo.show = false;
        this.getTableCfgPage();
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
  },// methods
  watch: {
    'connCfgDialogVo.form.connCfgId': {
      handler: function (val, oldVal) {
        if (val) {
          this.getTableOptions(false);
        }
      }, deep: true
    },
  },
  mounted() {
    this.getTableCfgPage();
  },
}
</script>

<style scoped>
/deep/ .conn-cfg-dialog-body {
  border-radius: 8px;
}

/deep/ .add-or-update-dialog-body {
  border-radius: 8px;
  min-height: 450px;
}
</style>