import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useNotificationStore } from './notificationStore'
import { useAuthStore } from './authStore'
import api from '../services/api'
import router from '../router'

export const useCarrinhoStore = defineStore('carrinho', () => {
  const itens = ref(new Map())

  const notificationStore = useNotificationStore()
  const totalItens = computed(() => somaTotalItens())

  const valorTotal = computed(() => somaValorTotalItens())

  function somaTotalItens() {
    let total = 0
    itens.value.forEach((value) => (total += value.quantidade))
    return total
  }

  function somaValorTotalItens() {
    let vTotal = 0
    itens.value.forEach((value) => {
      vTotal += value.produto.preco * value.quantidade
    })
    return vTotal
  }

  function adicionar(produto) {
    if (itens.value.has(produto.id)) {
      const itemExistente = itens.value.get(produto.id)
      itemExistente.quantidade++
    } else {
      itens.value.set(produto.id, { produto: produto, quantidade: 1 })
    }

    notificationStore.addNotification(`"${produto.nome}" foi adicionado ao carrinho!`, 'success')
    console.log('carrinho atual:', itens.value)
  }

  function incrementarQuantidade(produtoId) {
    const prod = itens.value.get(produtoId)
    prod.quantidade++
  }

  function decrementarQuantidade(produtoId) {
    const produto = itens.value.get(produtoId)
    if (produto.quantidade > 1) {
      produto.quantidade--
    } else {
      remover(produtoId)
    }
  }

  function remover(produtoId) {
    itens.value.delete(produtoId)
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
      itens: Array.from(itens.value.values()).map((item) => ({
        produtoId: item.produto.id,
        quantidade: item.quantidade,
      })),
    }

    try {
      await api.post(`/pedidos`, pedidoRequest)
      notificationStore.addNotification('Pedido feito com sucesso', 'success')

      itens.value.clear()
      await router.push('/')
    } catch (error) {
      console.error('Erro ao finalizar pedido:', error)
      notificationStore.addNotification('Erro ao fazer o pedido', 'error')
    }
  }
  return {
    itens,
    totalItens,
    valorTotal,
    adicionar,
    remover,
    finalizarCompra,
    incrementarQuantidade,
    decrementarQuantidade,
  }
})
