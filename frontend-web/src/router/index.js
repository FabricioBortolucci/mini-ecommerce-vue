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
    {
      path: '/carrinho',
      name: 'carrinho',
      component: () => import('../views/CarrinhoView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/registro',
      name: 'registro',
      component: () => import('../views/RegisterView.vue'),
    },
    {
      path: '/meus-pedidos',
      name: 'meus-pedidos',
      component: () => import('../views/MeusPedidosView.vue'),
    },
  ],
})

export default router
