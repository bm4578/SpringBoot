import { createRouter, createWebHistory } from "vue-router";
import Home from '../views/Home.vue'


const routes = [
    {
        path: "/",
        name: "Home",
        component: Home
    },{
    path: "/login",
        name: "login",
        component: ()=>import("../views/user/Login.vue")
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;