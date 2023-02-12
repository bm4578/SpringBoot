import { defineStore } from 'pinia'
export const userStore = defineStore({
    id: 'user',
    state: () => {
        return {
            token: ''
        }
    },
    actions:{
        setToken( token ){
            this.token = token;
        }
    },
    // 开启数据缓存
    persist: {
        enabled: true,
        strategies: [{
            key: 'minio_user',
            storage: localStorage,
            //paths: ['token']
        }]
    }
})