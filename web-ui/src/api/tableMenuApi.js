import request from '@/utils/request';

export function getTableCfgPageFn(data, page) {
    return request({
        url: '/tableCfg/tableCfgTablePage',
        method: 'post',
        data,
        params: page
    });
}

export function getTableOptionsFn(data, query) {
    return request({
        url: '/tableCfg/tableCfgOptions',
        method: 'post',
        data,
        params: query
    });
}

export function getTableAndColumnFromDsFn(data) {
    return request({
        url: '/tableCfg/tableAndColumnFromDs',
        method: 'post',
        data
    });
}

export function addTableCfgFn(data) {
    return request({
        url: '/tableCfg/add',
        method: 'post',
        data
    });
}

export function getColumnCfgByTableCfgFn(data) {
    return request({
        url: '/columnCfg/getByTableCfg',
        method: 'post',
        data
    });
}

export function updateTableCfgFn(data) {
    return request({
        url: '/tableCfg/update',
        method: 'post',
        data
    });
}

// -----------------------------------------------------------------------------------------------------------------
export function tableCfgRowStyleFn({row, rowIndex}) {
    return {backgroundColor: (rowIndex % 2 === 0 ? '#f0f9eb' : '#b7a476'), color: 'black'};
}

export function tableCfgCellStyleFn({row, column, rowIndex, columnIndex}) {
    if (['a', 'columnComment'].includes(column.property)) {
        return {textAlign: 'left'};
    }
    if (['id', 'userId', 'cfgName', 'extName', 'userName', 'password', 'createTime', 'updateTime'].includes(column.property)) {
        return {textAlign: 'center'};
    }
    return {textAlign: 'center'};
}

export function tableCfgHeaderCellStyleFn({row, column, rowIndex, columnIndex}) {
    return {backgroundColor: '#01847f', textAlign: 'center', color: 'yellow', fontSize: '10px'};
}

// -----------------------------------------------------------------------------------------------------------------
export function columnCfgRowStyleFn({row, rowIndex}) {
    return {backgroundColor: (rowIndex % 2 === 0 ? '#f0f9eb' : '#b7a476'), color: 'black'};
}

export function columnCfgCellStyleFn({row, column, rowIndex, columnIndex}) {
    if (['a', 'columnComment'].includes(column.property)) {
        return {textAlign: 'left'};
    }
    if (['id', 'userId', 'cfgName', 'extName', 'userName', 'password', 'createTime', 'updateTime'].includes(column.property)) {
        return {textAlign: 'center'};
    }
    return {textAlign: 'center'};
}

export function columnCfgHeaderCellStyleFn({row, column, rowIndex, columnIndex}) {
    return {backgroundColor: '#01847f', textAlign: 'center', color: 'yellow', fontSize: '10px'};
}