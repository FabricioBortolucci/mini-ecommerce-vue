<script setup>
import api from '@/services/api'
import { onMounted, ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useNotificationStore } from '@/stores/notificationStore'

const route = useRoute()
const router = useRouter()
const notification = useNotificationStore()

const produto = ref({
  nome: '',
  descricao: '',
  preco: '',
  imageUrl: '',
  fornecedorId: '',
})

const fornecedores = ref([])
const loading = ref(false)

const isEdicao = computed(() => route.params.id !== undefined)

onMounted(async () => {
  await carregarFornecedores()

  if (isEdicao.value) {
    await carregarProduto(route.params.id)
  }
})

const carregarFornecedores = async () => {
  try {
    const response = await api.get('/fornecedores')
    fornecedores.value = response.data
  } catch (error) {
    console.error('Erro ao carregar fornecedores', error)
  }
}

const carregarProduto = async (id) => {
  try {
    const response = await api.get(`/produtos/${id}`)
    const data = response.data
    produto.value = {
      nome: data.nome,
      descricao: data.descricao,
      preco: data.preco,
      imageUrl: data.imageUrl,
      fornecedorId: data.fornecedor ? data.fornecedor.id : '',
    }
  } catch (error) {
    notification.addNotification('Erro ao carregar produto', 'error')
    await router.push('/admin/produtos')
  }
}

const salvar = async () => {
  try {
    loading.value = true

    if (isEdicao.value) {
      await api.put(`/produtos/${route.params.id}`, produto.value)
      notification.addNotification('Produto atualizado com sucesso!', 'success')
    } else {
      await api.post('/produtos/criar', produto.value)
      notification.addNotification('Produto criado com sucesso!', 'success')
    }

    await router.push('/admin/produtos')
  } catch (error) {
    console.error('Erro ao salvar:', error)
    notification.addNotification('Erro ao salvar. Verifique os dados.', 'error')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="container mx-auto p-6 max-w-2xl">
    <h1 class="text-3xl font-bold text-gray-800 mb-6">
      {{ isEdicao ? 'Editar Produto' : 'Novo Produto' }}
    </h1>

    <form @submit.prevent="salvar" class="bg-white p-6 rounded-lg shadow-md space-y-4">
      <div>
        <label class="block text-gray-700 font-bold mb-2">Nome do Produto</label>
        <input
          v-model="produto.nome"
          type="text"
          required
          class="w-full border rounded px-3 py-2 focus:outline-blue-500"
        />
      </div>

      <div>
        <label class="block text-gray-700 font-bold mb-2">Descrição</label>
        <textarea
          v-model="produto.descricao"
          required
          class="w-full border rounded px-3 py-2 focus:outline-blue-500"
        ></textarea>
      </div>

      <div>
        <label class="block text-gray-700 font-bold mb-2">Preço (R$)</label>
        <input
          v-model="produto.preco"
          type="number"
          step="0.01"
          required
          class="w-full border rounded px-3 py-2 focus:outline-blue-500"
        />
      </div>

      <div>
        <label class="block text-gray-700 font-bold mb-2">URL da Imagem</label>
        <input
          v-model="produto.imageUrl"
          type="url"
          class="w-full border rounded px-3 py-2 focus:outline-blue-500"
        />
        <p class="text-xs text-gray-500 mt-1">Ex: https://i.imgur.com/...</p>
      </div>

      <div>
        <label class="block text-gray-700 font-bold mb-2">Fornecedor</label>
        <select
          v-model="produto.fornecedorId"
          required
          class="w-full border rounded px-3 py-2 focus:outline-blue-500 bg-white"
        >
          <option disabled value="">Selecione um fornecedor</option>
          <option v-for="f in fornecedores" :key="f.id" :value="f.id">
            {{ f.nome }} (CNPJ: {{ f.cnpj }})
          </option>
        </select>
      </div>

      <div class="flex justify-end space-x-4 mt-6">
        <RouterLink
          to="/admin/produtos"
          class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
        >
          Cancelar
        </RouterLink>
        <button
          type="submit"
          class="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700 font-bold"
          :disabled="loading"
        >
          {{ loading ? 'Salvando...' : 'Salvar' }}
        </button>
      </div>
    </form>
  </div>
</template>
