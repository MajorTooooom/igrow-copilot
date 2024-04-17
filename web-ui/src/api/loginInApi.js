import request from '@/utils/request';

export function loginFn(data) {
    return request({
        url: '/login/submit',
        method: 'post',
        data: data,
        headers: {
            enableAntiShake: true
        }
    })
}

export function signUpFn(data) {
    return request({
        url: '/login/signUp',
        method: 'post',
        data: data,
        headers: {
            enableAntiShake: true
        }
    })
}