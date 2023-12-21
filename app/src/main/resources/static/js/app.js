import { ref } from 'vue'
export default {
    setup() {
        const message = ref('Hello Vue!')
        return {
            message
        }
    },
    template: 
    `
    <h1>Hello App!</h1>
    <p>
      <router-link to="/">Go to Home</router-link>
      <router-link to="/about">Go to About</router-link>
    </p>
    <router-view></router-view>
    `
}