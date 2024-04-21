import request from '@/utils/request';


export function getGenSysUserVoFn(data) {
    return request({
        url: '/genSysUser/get',
        method: 'post',
        data
    });
}

export function updateGenSysUserVoFn(data) {
    return request({
        url: '/genSysUser/update',
        method: 'post',
        data
    });
}