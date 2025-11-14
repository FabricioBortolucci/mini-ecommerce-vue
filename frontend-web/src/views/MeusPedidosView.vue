<script setup>
import api from '@/services/api'
import { onMounted, ref } from 'vue'

const pedidos = ref([])

onMounted(() => {
  api
    .get(`/pedidos/meus-pedidos`)
    .then((response) => {
      pedidos.value = response.data
    })
    .catch((error) => {
      console.error('Error ao receber pedidos', error)
    })
})
</script>

<template>
  <div class="max-w-3xl mx-auto">
    <h1 class="text-3xl font-bold mb-6">Meus Pedidos</h1>

    <div v-if="pedidos.length > 0">
      <ul class="divide-y divide-gray-200 bg-white shadow-lg rounded-lg">
        <li
          v-for="pedido in pedidos"
          :key="pedido.id"
          class="flex items-center justify-between p-4"
        >
          <div>
            <h2 class="font-semibold">Id: {{ pedido.id }}</h2>
            <p class="text-gray-600">Data do Pedido: {{ pedido.dataPedido }}</p>
            <p class="text-gray-600">Valor Total: R$ {{ pedido.valorTotal }}</p>
            <p class="text-gray-600">Status Pedido: {{ pedido.status }}</p>
          </div>
        </li>
      </ul>
    </div>

    <div v-else class="text-center text-gray-500 mt-10">
      <p class="text-2xl">Lista de pedidos vazia.</p>
      <RouterLink to="/" class="text-blue-600 hover:underline">Voltar para a loja</RouterLink>
    </div>
  </div>
</template>
