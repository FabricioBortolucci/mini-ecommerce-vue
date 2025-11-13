import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCarrinhoStore = defineStore('carrinho', () => {
  const itens = ref([])

  const totalItens = computed(() => itens.value.length)

  const valorTotal = computed(() => {
    return itens.value.reduce((total, item) => total + item.preco, 0)
  })

  function adicionar(produto) {
    itens.value.push(produto)
    console.log('carrinho atual:', itens.value)
  }

  function remover(produtoId) {
    itens.value = itens.value.filter((item) => item.id !== produtoId)
  }

  return { itens, totalItens, valorTotal, adicionar, remover }
})
