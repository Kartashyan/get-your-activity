import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Activities from './components/Activities.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Activities',
    component: Activities,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;