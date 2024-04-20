import request from '@/utils/request';
import FileSaver from 'file-saver';
import axios from "axios";
import * as CommonConsts from "@/config/CommonConsts";

export function getTableCfgOptionsFn() {
    return request({
        url: '/optGen/optTableCfgOptions',
        method: 'post',
    })
}

export function getGenCfgOptionsFn(data) {
    return request({
        url: '/optGen/optGenCfgOptions',
        method: 'post',
        data
    })
}

export function renderToStringFn(data) {
    return request({
        url: '/optGen/renderToString',
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data',
        }
    })
}

export function renderToZipFn(data) {
    return request({
        url: '/optGen/renderToZip',
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data',
        }
    })
}

export function renderToDirFn(data) {
    return request({
        url: '/optGen/renderToDir',
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data',
        }
    })
}

export function downloadZipFn(missionId) {
    return axios({
        method: 'post',
        url: `/optGen/downloadZip`,
        params: {missionId}, // 使用 params 发送查询参数
        responseType: 'blob',  // 确保响应被处理为二进制数据
        headers: {
            'gen-sys-user-id': sessionStorage.getItem(CommonConsts.SESSION_USER_ID) || '',
        }
    }).then(response => {
        // 创建一个 Blob 对象
        const blob = new Blob([response.data], {type: 'application/zip'});
        // 使用 FileSaver.js 保存文件
        const fileName = `${missionId}.zip`;
        FileSaver.saveAs(blob, fileName);  // 触发文件下载
    }).catch(error => {
        console.error('Error downloading zip file:', error);
    });
}