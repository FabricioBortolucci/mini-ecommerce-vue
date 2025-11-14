import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useNotificationStore } from './notificationStore'
import { useAuthStore } from './authStore'
import api from '../services/api'
import router from '../router'

export const useCarrinhoStore = defineStore('carrinho', () => {
  const itens = ref([])
  const notificationStore = useNotificationStore()
  const totalItens = computed(() => itens.value.length)

  const valorTotal = computed(() => {
    return itens.value.reduce((total, item) => total + item.preco, 0)
  })

  function adicionar(produto) {
    itens.value.push(produto)

    notificationStore.addNotification(`"${produto.nome}" foi adicionado ao carrinho!`, 'success')
    console.log('carrinho atual:', itens.value)
  }

  function remover(produtoId) {
    itens.value = itens.value.filter((item) => item.id !== produtoId)
    notificationStore.addNotification(`removido do carrinho!`, 'warn')
  }

  async function finalizarCompra() {
    const authStore = useAuthStore()

    if (!authStore.isLoggedIn) {
      notificationStore.addNotification('Precisa estar logado para realizar esta ação', 'warn')
      await router.push('/login')
      return
    }

    const pedidoRequest = {
      itens: itens.value.map((prod) => ({
        produtoId: prod.id,
        quantidade: 1,
      })),
    }

    try {
      await api.post(`/pedidos`, pedidoRequest)
      notificationStore.addNotification('Pedido feito com sucesso', 'success')

      itens.value = []
      await router.push('/')
    } catch (error) {
      console.error('Erro ao finalizar pedido:', error)
      notificationStore.addNotification('Erro ao fazer o pedido', 'error')
    }
  }
  return { itens, totalItens, valorTotal, adicionar, remover, finalizarCompra }
})
