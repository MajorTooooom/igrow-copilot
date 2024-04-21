<template>
  <div>
    <el-form :inline="true" ref="envCfgFormVo" :model="envCfgFormVo" label-width="120px" size="medium" class="env-cfg-form-class">
      <el-form-item label="表&表字段配置">
        <el-select v-model="envCfgFormVo.tableCfgId" placeholder="请选择表&表字段配置" clearable filterable style="min-width: 400px;margin-right: -40px;">
          <el-option v-for="item in tableCfgOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渲染配置">
        <el-select v-model="envCfgFormVo.genCfgId" placeholder="请选择渲染配置" clearable filterable style="min-width: 100%;">
          <el-option v-for="item in genCfgOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-upload
            ref="upload"
            action=""
            :file-list="uploadFileList"
            :multiple="true"
            :auto-upload="false"
            :limit="10"
            style="display: inline-block;margin-right: 10px;"
            :show-file-list="false"
            :on-change="uploadHandleChange"
            :on-remove="uploadHandleRemove"
            :before-upload="beforeUpload">
          <el-tooltip class="item" effect="dark" content="数量限制10" placement="top-start">
            <el-button size="medium" type="info" plain>选择模板文件&nbsp;&nbsp;<i class="fa fa-file" style="color: #d99f2e;"></i></el-button>
          </el-tooltip>
        </el-upload>
        <el-button size="medium" type="info" plain @click="uploadFileListDialogVo.show=true">（查看已选）</el-button>
        <el-tooltip class="item" effect="dark" content="web展示" placement="top-start">
          <el-button type="warning" @click="renderToString">Web预览</el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="zip打包下载" placement="top-start">
          <el-button type="primary" @click="renderToZip">打包下载</el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="内置模板直接生成到项目目录下,网页上传的模板在网页端查看" placement="top-start">
          <el-button type="primary" @click="beforeRenderToDir">渲染至目录</el-button>
        </el-tooltip>
      </el-form-item>
    </el-form>
    <el-dialog
        :top="'30vh'"
        title="查看已选文件(排除同名文件或非“.ftl”/“.vm”文件)"
        :custom-class="'upload-file-list-dialog'"
        :visible.sync="uploadFileListDialogVo.show"
        width="45%">
      <div style="">
        <p v-for="item in uploadFileList" :key="item.name"><span>{{ item.name }}</span></p>
      </div>
      <span slot="footer" class="dialog-footer"><el-button @click="uploadFileListDialogVo.show = false">朕已阅</el-button></span>
    </el-dialog>
    <div class="render-result-tab-clazz">
      <el-empty description="暂无预览数据,请选择web预览" v-if="!(renderResults&&renderResults.length>0)"></el-empty>
      <el-tabs type="border-card" :tab-position="'left'" style="min-height:400px;" :stretch="true" v-if="(renderResults&&renderResults.length>0)">
        <el-tab-pane :label="data.mainName+'.'+data.extName+(data.success?'':'（报错）')" v-for="data in renderResults" style="min-height: 600px;">
          <div style="height: 100%; overflow-y: auto;">
            <el-alert :title="getRenderResultTitle(data)" :type="data.success?'success':'error'" :closable="false"></el-alert>
            <!---->
            <el-input v-if="!data.success" type="textarea" :autosize="{ minRows: 5,maxRows:15}" placeholder="" v-model="data.errorMessage" class="textarea-font-size"></el-input>
            <el-alert v-if="!data.success" title="模板原文" :type="data.renderSuccess?'success':'error'" :closable="false"></el-alert>
            <el-input v-if="!data.success" type="textarea" :autosize="{ minRows: 5,maxRows:15}" placeholder="" v-model="data.templateContent" class="textarea-font-size"></el-input>
            <!---->
            <el-input v-if="data.success" class="textarea-font-size" type="textarea" :autosize="{ minRows: 5,maxRows:15}" placeholder="" v-model="data.stringResult"></el-input>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {Notification, MessageBox, Message, Loading} from 'element-ui';
import request from '@/utils/request';
import * as CommonConsts from '@/config/CommonConsts';
import {
  getTableCfgOptionsFn,
  getGenCfgOptionsFn,
  renderToStringFn,
  renderToZipFn,
  renderToDirFn,
  downloadZipFn,
  getLocalHostModeFn,
} from '@/api/optMenuApi';

export default {
  name: "OptMenuPage",
  components: {},
  data() {
    return {
      localHostMode: false,
      envCfgFormVo: {
        tableCfgId: '',
        genCfgId: '',
        optMode: '',
      },
      tableCfgOptions: [],
      genCfgOptions: [],
      uploadFileList: [],
      uploadFileListDialogVo: {
        show: false,
      },
      renderResults: [],
    }
  },
  methods: {
    getTableCfgOptions() {
      // this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      getTableCfgOptionsFn().then(res => {
        this.tableCfgOptions = res.data;
      }).finally(() => {
        // this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    getGenCfgOptions() {
      // this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      getGenCfgOptionsFn().then(res => {
        this.genCfgOptions = res.data;
      }).finally(() => {
        // this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    uploadHandleChange(file, fileList) {
      // 排除`this.uploadFileList`中已存在的同名文件
      fileList = fileList.filter(item => !this.uploadFileList.some(i => i.name === item.name));
      // 只支持ftl以及vm的文件格式
      fileList = fileList.filter(item => item.name.endsWith('.ftl') || item.name.endsWith('.vm'));
      // 合并
      this.uploadFileList = this.uploadFileList.concat(fileList);
      this.uploadFileListDialogVo.show = true;
    },
    uploadHandleRemove(file, fileList) {
      this.uploadFileList = fileList;
    },
    beforeUpload() {
      return false;
    },
    getRenderResultTitle(data) {
      return `文件名[${data.mainName}.${data.extName}]-------渲染结果:${data.success ? '成功' : '失败'}`;
    },
    verifyBeforeRender() {
      if (!this.envCfgFormVo.tableCfgId) {
        Message.error('请选择表&表字段配置');
        return false;
      }
      if (!this.envCfgFormVo.genCfgId) {
        Message.error('请选择渲染配置');
        return false;
      }
      // 文件数量限制10
      if (this.uploadFileList.length > 10) {
        Message.error('数量限制10');
        return false;
      }
      // 单文件大小不能超过1MB,总文件大小不能超过3MB
      let totalSize = 0;
      for (let i = 0; i < this.uploadFileList.length; i++) {
        if (this.uploadFileList[i].size > 1 * 1024 * 1024) {
          this.$message.warning(`'单文件大小不能超过1MB：['${this.uploadFileList[i].name}](${this.uploadFileList[i].size} bytes)`)
          this.uploadFileList = [];
          return false;
        } else {
          totalSize += this.uploadFileList[i].size;
        }
      }
      if (totalSize > 3 * 1024 * 1024) {
        this.$message.warning('总文件大小不能超过3MB')
        this.uploadFileList = [];
        return false;
      }
      // all pass
      return true;
    },
    getRenderFormData() {
      // 下面的代码将创建一个空的FormData对象:
      const formData = new FormData()
      // 你可以使用FormData.append来添加键/值对到表单里面；
      this.uploadFileList.forEach((file) => {
        formData.append('file', file.raw)
      })
      // 添加自定义参数，不传可删除
      formData.append('tableCfgId', this.envCfgFormVo.tableCfgId);
      formData.append('genCfgId', this.envCfgFormVo.genCfgId);
      return formData;
    },
    renderToString() {
      if (!this.verifyBeforeRender()) {
        return false;
      }
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      renderToStringFn(this.getRenderFormData()).then(res => {
        this.renderResults = res.data;
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    renderToZip() {
      if (!this.verifyBeforeRender()) {
        return false;
      }
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      renderToZipFn(this.getRenderFormData()).then(res => {
        this.renderResults = res.data.renderResults;
        downloadZipFn(res.data.missionId);
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    beforeRenderToDir() {
      if (!this.localHostMode) {
        Message.warning('服务器模式下不支持此操作');
        return false;
      }
      if (!this.verifyBeforeRender()) {
        return false;
      }
      // 二次确认
      MessageBox.confirm('确认渲染至目录？请确保你已经备份了项目代码', '二次确认', {
        confirmButtonText: '确定,我已备份代码',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.renderToDir();
      }).catch(() => {
        Message.info('已取消');
      });
    },
    renderToDir() {
      if (!this.verifyBeforeRender()) {
        return false;
      }
      this.$bus.$emit(CommonConsts.FULL_SCREEN_OPEN);
      renderToDirFn(this.getRenderFormData()).then(res => {
        this.renderResults = res.data;
      }).finally(() => {
        this.$bus.$emit(CommonConsts.FULL_SCREEN_CLOSE);
      });
    },
    getLocalHostMode() {
      getLocalHostModeFn().then(res => {
        this.localHostMode = res.data;
        console.log("当前模式:", this.localHostMode ? "本地" : "云服务器");
      });
    },
  },// methods
  watch: {
    // 'searchParamVo.topPath': {handler: function (val, oldVal) {if (val) {this.searchParamVo.topPath = val;this.searchParamVo.topPath = '';}}, deep: true},
  },
  mounted() {
    this.getTableCfgOptions();
    this.getGenCfgOptions();
    this.getLocalHostMode();
  },
}
</script>

<style scoped>
.env-cfg-form-class {
  padding: 20px;
//border: #cb7c44 2px dotted; border-radius: 4px; text-align: right;
}

/deep/ .upload-file-list-dialog {
  border-radius: 9px;
}

.render-result-tab-clazz {
  padding: 20px;
//border: #cb7c44 2px dotted; border-radius: 4px; margin-top: 10px;
}

.textarea-font-size {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  font-size: 20px;
  color: #cb7c44;
}

</style>