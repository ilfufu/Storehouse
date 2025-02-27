import VueRouter from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../login/Login')
    },
    {
        path: '/Index',
        name: 'index',
        component: () => import('../background/Index'),
        children: [
            {
                path: '/Center',
                name: 'Center',
                meta: {
                    title: '用户中心'
                },
                component: () => import('../usercenter/Center'),
            },
            {
                path: '/Admin',
                name: 'Admin',
                meta: {
                    title: '管理员管理'
                },
                component: () => import('../manage/AdminManage'),
            },
            {
                path: '/User',
                name: 'User',
                meta: {
                    title: '用户管理'
                },
                component: () => import('../manage/UserManage.vue'),
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err);
}

export default router;