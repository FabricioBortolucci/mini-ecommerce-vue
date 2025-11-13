import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
    },
    {
      path: '/produto/:id',
      name: 'detalhe-produto',
      component: () => import('../views/ProdutoDetalheView.vue'),
    },
    {
      path: '/detalhe-fornecedor/:id',
      name: 'detalhe-fornecedor',
      component: () => import('../views/DetalheFornecedorView.vue'),
    },
  ],
})

export default router
