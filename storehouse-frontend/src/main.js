import Vue from 'vue'
import App from './App.vue'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN';
Vue.use(ElementUI, {locale})

import './assets/global.css'

import axios from 'axios';
Vue.prototype.$axios = axios;

// 本地部署时使用
Vue.prototype.$httpUrl = 'http://localhost:8090';

// 公网部署时使用，记得改ip
// Vue.prototype.$httpUrl = 'http://47.110.234.156:8090';

import VueRouter from 'vue-router';
import router from './router';
Vue.use(VueRouter);

import store from './store';

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
