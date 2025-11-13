<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

const fornecedor = ref(null)
const loading = ref(true)

const route = useRoute()

onMounted(() => {
  const fornecedorId = route.params.id

  axios
    .get(`http://localhost:8080/api/fornecedores/${fornecedorId}`)
    .then((response) => {
      fornecedor.value = response.data
      loading.value = false
    })
    .catch((error) => {
      console.error('Erro ao buscar fornecedor:', error)
      loading.value = false
    })
})
</script>

<template>
  <div>
    <div v-if="loading" class="text-center text-gray-500 mt-10">
      Carregando detalhes do fornecedor...
    </div>

    <div v-else-if="fornecedor" class="max-w-4xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-8 p-4">
      <div>
        <img
          :src="fornecedor.profileImageUrl"
          :alt="fornecedor.nome"
          class="w-full h-auto object-cover rounded-lg shadow-lg"
        />
      </div>

      <div class="flex flex-col justify-center">
        <p class="text-lg text-gray-700 mb-6">Nome: {{ fornecedor.nome }}</p>
        <p class="text-lg text-gray-700 mb-6">Email: {{ fornecedor.email }}</p>
        <p class="text-lg text-gray-700 mb-6">Telefone: {{ fornecedor.telefone }}</p>
        <p class="text-lg text-gray-700 mb-6">CNPJ: {{ fornecedor.cnpj }}</p>
      </div>
    </div>
  </div>
</template>
