import request from '@/utils/request';

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