import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// 公共路由
export const constantRoutes = [

    {
        path: '/', // 这个代表首页
        component: (resolve) => require(['@/pages/index'], resolve),
        name: '首页'
    },
    {
        path: '/editTable/:tableId(\\d+)',
        component: (resolve) => require(['@/pages/editTable'], resolve),
        name: '修改生成配置'
    },
    {
        path: '/404',
        component: (resolve) => require(['@/pages/error/404'], resolve),
        hidden: true
    },
    {
        path: '/401',
        component: (resolve) => require(['@/pages/error/401'], resolve),
        hidden: true
    },
    //这个*匹配必须放在最后，将改路由配置放到所有路由的配置信息的最后，否则会其他路由path匹配造成影响。
    // {
    //     path: '*',
    //     redirect: '/404',
    //     hidden: true
    // }

]

export default new Router({
    base: process.env.VUE_APP_APP_NAME ? process.env.VUE_APP_APP_NAME : "/",
    mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})
