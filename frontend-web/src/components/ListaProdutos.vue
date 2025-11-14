<script setup>
import { ref, onMounted } from 'vue'
import api from '@/services/api'

const produtos = ref([])

onMounted(() => {
  api
    .get('/produtos')
    .then((response) => {
      produtos.value = response.data
    })
    .catch((error) => {
      console.error('Erro ao buscar produtos:', error)
    })
})
</script>

<template>
  <div class="max-w-2xl mx-auto p-4">
    <h1 class="text-3xl font-bold text-center text-gray-800 mb-6">Nossos Produtos</h1>

    <div v-if="produtos.length > 0" class="bg-white shadow-md rounded-lg">
      <ul class="divide-y divide-gray-200">
        <RouterLink v-for="produto in produtos" :key="produto.id" :to="'/produto/' + produto.id">
          <li class="p-4 flex items-center space-x-4 hover:bg-gray-50 cursor-pointer">
            <img
              :src="produto.imageUrl"
              alt="Imagem do produto"
              class="w-16 h-16 object-cover rounded"
            />

            <div class="flex-1">
              <h2 class="text-lg font-semibold text-blue-700 hover:underline">
                {{ produto.nome }}
              </h2>
              <p class="text-gray-600">{{ produto.descricao }}</p>
            </div>

            <span class="text-xl font-medium text-gray-900"> R$ {{ produto.preco }} </span>
          </li>
        </RouterLink>
      </ul>
    </div>

    <div v-else class="text-center text-gray-500 mt-10">Carregando produtos...</div>
  </div>
</template>

<style scoped></style>
