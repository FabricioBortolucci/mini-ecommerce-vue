import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useNotificationStore } from './notificationStore'

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

  return { itens, totalItens, valorTotal, adicionar, remover }
})
