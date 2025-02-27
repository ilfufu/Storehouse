import VueRouter from 'vue-router';

const routes = [
    {
        path: '/',
        name: '登录',
        component: () => import('../login/Login')
    },
    {
        path: '/Index',
        name: '主页',
        component: () => import('../background/Index'),
        children: [
            {
                path: '/Center',
                name: '用户中心',
                meta: {
                    title: '用户中心'
                },
                component: () => import('../usercenter/Center'),
            },

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

export function resetRouter() {
    router.matcher = new VueRouter({
        mode: 'history',
        routes: []
    }).matcher
}

export default router;