import { createApp } from 'vue'
import App from './App.vue'
//路由组件
import router from "./router";
//初始化项目UI 采用：https://meyerweb.com/eric/tools/css/reset/
import './assets/css/reset.css'
//Element-Plus 采用：https://element-plus.gitee.io/zh-CN/guide/design.html
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//引入pinia 来作为持久化存储
import store from './store'
createApp(App).use(router).use(store).use(ElementPlus).mount('#app')
