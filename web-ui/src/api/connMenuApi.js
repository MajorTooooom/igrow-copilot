import request from '@/utils/request';

export function addFn(data) {
    return request({
        url: '/connCfg/add',
        method: 'post',
        data: data,
    });
}

export function deleteFn(data) {
    return request({
        url: '/connCfg/delete',
        method: 'post',
        data: data,
    });

}

export function pageFn(data, query) {
    return request({
        url: '/connCfg/page',
        method: 'post',
        data: data,
        params: query,
    });
}

export function updateFn(data) {
    return request({
        url: '/connCfg/update',
        method: 'post',
        data: data,
    });
}

export function getConnCfgOptionsFn() {
    return request({
        url: '/connCfg/connCfgOptions',
        method: 'post'
    })
}

/**
 * 表格样式_行样式_根据行号换不同的背景色
 */
export function rowStyleFn({row, rowIndex}) {
    return {backgroundColor: (rowIndex % 2 === 0 ? '#f0f9eb' : '#b7a476'), color: 'black'};
}

/**
 * 表格样式_单元格样式_部分列的单元格希望内容居中有些又希望左对齐
 *     //根据列的字段名进行判断
 *     // if (['name', 'absPath'].includes(column.property)) {return {textAlign: 'left'};}
 *     //根据下标进行判断,下标从0开始
 *     // if ([1, 2, 3, 4, 5].includes(columnIndex)) {return {textAlign: 'left'};}
 */
export function cellStyleFn({row, column, rowIndex, columnIndex}) {
    if (['a', 'b'].includes(column.property)) {
        return {textAlign: 'left'};
    }
    if (['id', 'userId', 'cfgName', 'extName', 'userName', 'password', 'createTime', 'updateTime'].includes(column.property)) {
        return {textAlign: 'center'};
    }
    return {textAlign: 'center'};
}

export function headerCellStyleFn({row, column, rowIndex, columnIndex}) {
    return {backgroundColor: '#01847f', textAlign: 'center', color: 'yellow', fontSize: '10px'};
}