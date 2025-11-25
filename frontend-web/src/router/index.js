import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import { useRequestLogStore } from '@/stores/requestLogStore.js'

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
      name: 'admin-novo-produto',
      component: () => import('../views/admin/AdminProdutoForm.vue'),
    },
    {
      path: '/admin/produtos/editar/:id',
      name: 'admin-editar-produto',
      component: () => import('../views/admin/AdminProdutoForm.vue'),
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/aboutView.vue'),
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

router.afterEach((to, from) => {
  const logStore = useRequestLogStore()

  logStore.addLog({
    method: 'ROUTE', // Um método "falso" para identificar navegação
    url: to.fullPath,
    status: 'OK',
    statusText: 'Navegação Interna',
    duration: 0,
    type: 'info'
  })
})
export default router
