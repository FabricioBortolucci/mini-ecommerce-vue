<script setup>
import api from '@/services/api.js'
import { onMounted, ref } from 'vue'
import { useNotificationStore } from '@/stores/notificationStore.js'

const produtos = ref([])
const loading = ref(true)
const notification = useNotificationStore()

onMounted(async () => {
  await carregarProdutos()
})

const carregarProdutos = async () => {
  loading.value = true
  try {
    const response = await api.get('/produtos/admin')
    produtos.value = response.data
  } catch (error) {
    console.error('Erro ao carregar produtos:', error)
    notification.addNotification('Erro ao carregar produtos', 'error')
  } finally {
    loading.value = false
  }
}

const deletarProduto = async (id) => {
  if (!confirm('Tem certeza que deseja excluir este produto?')) return

  try {
    await api.delete(`/produtos/${id}`)
    notification.addNotification('Produto excluído com sucesso!', 'success')
    await carregarProdutos()
  } catch (error) {
    console.error('Erro ao deletar:', error)
    notification.addNotification('Erro ao excluir (Você é Admin?)', 'error')
  }
}

const ativarProduto = async (id) => {
  if (!confirm('Tem certeza que deseja ativar este produto?')) return

  try {
    await api.put(`/produtos/ativar/${id}`)
    notification.addNotification('Produto ativado com sucesso!', 'success')
    await carregarProdutos()
  } catch (error) {
    console.error('Erro ao ativar:', error)
    notification.addNotification('Erro ao ativar (Você é Admin?)', 'error')
  }
}
</script>

<template>
  <div class="container mx-auto p-6">
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-3xl font-bold text-gray-800">Gerenciar Produtos</h1>
      <RouterLink
        to="/admin/produtos/novo"
        class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 font-bold"
      >
        + Novo Produto
      </RouterLink>
    </div>

    <div v-if="loading" class="text-center text-gray-500">Carregando...</div>

    <div v-else class="bg-white shadow-md rounded-lg overflow-hidden">
      <table class="min-w-full leading-normal">
        <thead>
          <tr>
            <th
              class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Produto
            </th>
            <th
              class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Preço
            </th>
            <th
              class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-right text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Ações
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="produto in produtos" :key="produto.id">
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <div class="flex items-center">
                <div class="flex-shrink-0 w-10 h-10">
                  <img
                    class="w-full h-full rounded-full object-cover"
                    :src="produto.imageUrl"
                    alt=""
                  />
                </div>
                <div class="ml-3">
                  <p class="text-gray-900 whitespace-no-wrap font-bold">{{ produto.nome }}</p>
                  <p class="text-gray-600 whitespace-no-wrap text-xs">{{ produto.id }}</p>
                </div>
              </div>
            </td>
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <p class="text-gray-900 whitespace-no-wrap">R$ {{ produto.preco }}</p>
            </td>
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm text-right">
              <RouterLink
                :to="`/admin/produtos/editar/${produto.id}`"
                class="text-blue-600 hover:text-blue-900 mr-4 font-bold mouse-pointer"
              >
                Editar
              </RouterLink>
              <button
                @click="deletarProduto(produto.id)"
                v-if="produto.ativo"
                class="text-red-600 hover:text-red-900 font-bold mouse-pointer"
              >
                Desativar
              </button>
              <button
                v-else
                @click="ativarProduto(produto.id)"
                class="text-green-500 hover:text-green-900 font-bold mouse-pointer"
              >
                Ativar
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
