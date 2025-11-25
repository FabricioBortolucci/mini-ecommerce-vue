<script setup>
import { ref, onMounted } from 'vue'
import api from '@/services/api'
import { useCarrinhoStore } from '@/stores/carrinhoStore' // 1. Importe o Store

const produtos = ref([])
const loading = ref(true)
const carrinho = useCarrinhoStore() // 2. Inicialize o Store

onMounted(() => {
  api
    .get('/produtos')
    .then((response) => {
      produtos.value = response.data
    })
    .catch((error) => {
      console.error('Erro ao buscar produtos:', error)
    })
    .finally(() => {
      loading.value = false
    })
})
</script>

<template>
  <div class="max-w-6xl mx-auto p-4">
    <h1 class="text-3xl font-bold text-center text-gray-800 mb-8">Nossos Produtos</h1>

    <div v-if="loading" class="text-center text-gray-500 mt-10">
      <p class="text-xl animate-pulse">Carregando vitrine...</p>
    </div>

    <div
      v-else-if="produtos.length > 0"
      class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6"
    >
      <div
        v-for="produto in produtos"
        :key="produto.id"
        class="bg-white rounded-xl shadow-md hover:shadow-xl transition-shadow duration-300 overflow-hidden flex flex-col"
      >
        <RouterLink :to="'/produto/' + produto.id" class="block overflow-hidden group">
          <img
            :src="produto.imageUrl || 'https://via.placeholder.com/300'"
            :alt="produto.nome"
            class="w-full h-48 object-cover transform group-hover:scale-105 transition-transform duration-300"
          />
        </RouterLink>

        <div class="p-4 flex flex-col flex-grow">
          <div class="mb-2">
            <h2 class="text-lg font-bold text-gray-800 truncate" :title="produto.nome">
              <RouterLink :to="'/produto/' + produto.id" class="hover:text-blue-600">
                {{ produto.nome }}
              </RouterLink>
            </h2>
            <p class="text-sm text-gray-500 line-clamp-2 h-10 mb-2">
              {{ produto.descricao }}
            </p>
          </div>

          <div class="mt-auto pt-4 border-t border-gray-100 flex items-center justify-between">
            <span class="text-xl font-bold text-green-600"> R$ {{ produto.preco }} </span>

            <button
              @click="carrinho.adicionar(produto)"
              class="bg-blue-100 text-blue-600 p-2 rounded-full hover:bg-blue-600 hover:text-white transition-colors duration-200"
              title="Adicionar ao Carrinho"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
              </svg>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="text-center text-gray-500 mt-10">Nenhum produto encontrado.</div>
  </div>
</template>

<style scoped>
/* line-clamp ajuda a limitar o texto da descrição em 2 linhas */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
