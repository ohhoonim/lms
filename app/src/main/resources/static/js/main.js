import { createApp } from 'vue'
import VueRouter from 'vue-router'

import App from './app.js'

// vue-router init setting example
const Home = { template: '<div>Home</div>' }
const About = { template: '<div>About</div>' }

const routes = [
    { path: '/', component: Home },
    { path: '/about', component: About },
]

const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes, 
})

createApp(App)
.use(router)
.mount('#app')