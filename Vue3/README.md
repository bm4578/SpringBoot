1. [Vite](https://cn.vitejs.dev/) 来创建一个 [vue3](https://cn.vuejs.org/guide/scaling-up/tooling.html#project-scaffolding) 项目
    ```shell
    # npm 6.x
    npm create vite@latest my-vue-app --template vue
   
    # npm 7+, extra double-dash is needed:
    npm create vite@latest my-vue-app -- --template vue
    ```
2. 安装路由
   ```shell
   npm install vue-router@4 -S
   ```
3. 安装插件:unplugin-auto
   ```shell
   npm i unplugin-auto-import -D
   ```