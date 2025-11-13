<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import { useCarrinhoStore } from '../stores/carrinhoStore'

const produto = ref(null)
const loading = ref(true)

const route = useRoute()

const carrinho = useCarrinhoStore()

onMounted(() => {
  const produtoId = route.params.id

  axios
    .get(`http://localhost:8080/api/produtos/${produtoId}`)
    .then((response) => {
      produto.value = response.data
      loading.value = false
    })
    .catch((error) => {
      console.error('Erro ao buscar produto:', error)
      loading.value = false
    })
})
</script>

<template>
  <div>
    <div v-if="loading" class="text-center text-gray-500 mt-10">
      Carregando detalhes do produto...
    </div>

    <div v-else-if="produto" class="max-w-4xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-8 p-4">
      <div>
        <img
          :src="produto.imageUrl"
          :alt="produto.nome"
          class="w-full h-auto object-cover rounded-lg shadow-lg"
        />
      </div>

      <div class="flex flex-col justify-center">
        <h1 class="text-4xl font-bold text-gray-900 mb-4">{{ produto.nome }}</h1>
        <p class="text-lg text-gray-700 mb-6">{{ produto.descricao }}</p>
        <div v-if="produto.fornecedor" class="mt-4">
          <p class="text-gray-600">
            Vendido e entregue por:
            <RouterLink
              :to="'/detalhe-fornecedor/' + produto.fornecedor.id"
              class="text-blue-600 font-bold hover:underline"
            >
              {{ produto.fornecedor.nome }}
            </RouterLink>
          </p>
        </div>
        <div class="text-3xl font-light text-gray-900 mb-6">R$ {{ produto.preco }}</div>

        <button
          @click="carrinho.adicionar(produto)"
          class="w-full bg-blue-600 text-white font-bold py-3 px-6 rounded-lg hover:bg-blue-700 transition duration-300 cursor-pointer"
        >
          Adicionar ao Carrinho
        </button>
      </div>
    </div>
  </div>
</template>
