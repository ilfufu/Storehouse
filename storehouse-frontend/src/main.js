import Vue from 'vue'
import App from './App.vue'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

import './assets/global.css'

import axios from 'axios';
Vue.prototype.$axios = axios;
Vue.prototype.$httpUrl = 'http://localhost:8090';

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
