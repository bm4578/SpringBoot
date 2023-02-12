import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
//自动导入vue和vue-router相关函数
import AutoImport from 'unplugin-auto-import/vite'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
      vue(),
      AutoImport({
      imports:['vue','vue-router']
    })
  ],
  server:{
    proxy:{
      '/api':'http://localhost:1266'
    }
  }
})
