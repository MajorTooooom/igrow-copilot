import request from '@/utils/request';

export function addGenCfgFn(data) {
    return request({
        url: '/genCfg/add',
        method: 'post',
        data
    });
}

export function updateGenCfgFn(data) {
    return request({
        url: '/genCfg/update',
        method: 'post',
        data
    });
}

export function deleteGenCfgFn(data) {
    return request({
        url: '/genCfg/delete',
        method: 'post',
        data
    });
}

export function pageGenCfgFn(data, query) {
    return request({
        url: '/genCfg/page',
        method: 'post',
        data,
        params: query
    });
}

// -----------------------------------------------------------------------------------------------------------------
export function genCfgRowStyleFn({row, rowIndex}) {
    return {backgroundColor: (rowIndex % 2 === 0 ? '#f0f9eb' : '#b7a476'), color: 'black'};
}

export function genCfgCellStyleFn({row, column, rowIndex, columnIndex}) {
    if (['a', 'columnComment'].includes(column.property)) {
        return {textAlign: 'left'};
    }
    if (['id', 'userId', 'cfgName', 'extName', 'userName', 'password', 'createTime', 'updateTime'].includes(column.property)) {
        return {textAlign: 'center'};
    }
    return {textAlign: 'center'};
}

export function genCfgHeaderCellStyleFn({row, column, rowIndex, columnIndex}) {
    return {backgroundColor: '#01847f', textAlign: 'center', color: 'yellow', fontSize: '10px'};
}

// -----------------------------------------------------------------------------------------------------------------