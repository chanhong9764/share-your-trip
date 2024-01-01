const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,

    chainWebpack: (config) => {
        config.module.rules.delete('svg');

        config.module
            .rule('svg')
            .test(/\.(svg)(\?.*)?$/)
            .use('vue-loader')
            .loader('vue-loader')
            .end()
            .use('vue-svg-loader')
            .loader('vue-svg-loader');
    },
    devServer: {
        proxy: {
            '/api': {
                target: 'http://192.168.0.53:8080',
            },
        },
        client: {
            overlay: false,
        },
    },
});
