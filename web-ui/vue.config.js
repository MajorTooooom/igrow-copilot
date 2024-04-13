const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,
    // 解决页面覆盖报错信息 Uncaught runtime errors
    configureWebpack: {
        devServer: {
            client: {overlay: false},
        },
    },
    devServer: {
        open: true,
        proxy: 'http://localhost:8868',
    },
})
