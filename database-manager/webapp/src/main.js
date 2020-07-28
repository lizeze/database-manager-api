import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from './axios/index'
import './assets/icon-font/iconfont'
import './assets/icon-font/iconfont.css'

window.axios = axios
// import '//at.alicdn.com/t/font_1940883_sm35vgt7zh.js'

Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
