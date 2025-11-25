import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

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
    {
      path: '/admin/produtos',
      name: 'admin-produtos',
      component: () => import('../views/admin/AdminProdutoList.vue'),
    },
    {
      path: '/admin/produtos/novo',
      component: () => import('../views/admin/AdminProdutoForm.vue'),
    },
    {
      path: '/admin/produtos/editar/:id',
      component: () => import('../views/admin/AdminProdutoForm.vue'),
    },
  ],
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  if (to.path.startsWith('/admin')) {
    if (!authStore.isAdmin) {
      return next('/')
    }
  }
  next()
})

export default router
