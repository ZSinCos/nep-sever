import { createRouter, createWebHashHistory } from 'vue-router'
import Index from '../views/Index.vue'

const routes = [
  { path: '/', name: 'home', component: Index },
  { path: '/index', name: 'Index', component: Index }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
