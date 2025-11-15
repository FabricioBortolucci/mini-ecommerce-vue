<script setup>
import api from '../services/api'
import { onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'

const pedidos = ref([])
const loading = ref(true)

onMounted(() => {
  api
    .get(`/pedidos/meus-pedidos`)
    .then((response) => {
      pedidos.value = response.data
    })
    .catch((error) => {
      console.error('Error ao receber pedidos', error)
    })
    .finally(() => {
      loading.value = false
    })
})


const formatarData = (dataString) => {
  if (!dataString) return ''
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  }
  return new Date(dataString).toLocaleDateString('pt-BR', options)
}


const getStatusClass = (status) => {
  switch (status) {
    case 'PROCESSANDO':
      return 'bg-yellow-100 text-yellow-800'
    case 'ENVIADO':
      return 'bg-blue-100 text-blue-800'
    case 'ENTREGUE':
      return 'bg-green-100 text-green-800'
    case 'CANCELADO':
      return 'bg-red-100 text-red-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}
</script>

<template>
  <div class="max-w-4xl mx-auto">
    <h1 class="text-3xl font-bold mb-6">Meus Pedidos</h1>

    <div v-if="loading" class="text-center text-gray-500 mt-10">
      <p class="text-2xl">Buscando seus pedidos...</p>
    </div>

    <div v-else-if="!loading && pedidos.length === 0" class="text-center text-gray-500 mt-10">
      <p class="text-2xl">Você ainda não fez nenhum pedido.</p>
      <RouterLink to="/" class="text-blue-600 hover:underline">Voltar para a loja</RouterLink>
    </div>

    <div v-else class="space-y-6">
      <div
        v-for="pedido in pedidos"
        :key="pedido.id"
        class="bg-white shadow-lg rounded-lg overflow-hidden"
      >
        <div class="bg-gray-50 p-4 sm:p-6 border-b border-gray-200">
          <div class="flex flex-wrap justify-between items-center gap-4">
            <div>
              <h2 class="text-xl font-bold text-gray-900">Pedido #{{ pedido.id }}</h2>
              <p class="text-sm text-gray-600">Feito em: {{ formatarData(pedido.dataPedido) }}</p>
            </div>
            <div>
              <span
                :class="getStatusClass(pedido.status)"
                class="text-sm font-semibold px-3 py-1 rounded-full"
              >
                {{ pedido.status }}
              </span>
            </div>
            <div class="text-right">
              <p class="text-sm text-gray-600">Valor Total</p>
              <p class="text-2xl font-bold text-gray-900">R$ {{ pedido.valorTotal }}</p>
            </div>
          </div>
        </div>

        <div class="p-4 sm:p-6">
          <h3 class="text-lg font-semibold mb-4">Itens Inclusos:</h3>
          <ul class="divide-y divide-gray-200">
            <li v-for="item in pedido.itens" :key="item.id" class="flex items-center py-4">
              <img
                :src="item.produto.imageUrl || 'https://via.placeholder.com/150'"
                :alt="item.produto.nome"
                class="w-16 h-16 rounded-md object-cover mr-4 shadow"
              />
              <div class="flex-grow">
                <p class="font-semibold text-gray-800">{{ item.produto.nome }}</p>
                <p class="text-sm text-gray-600">
                  {{ item.quantidade }} un. x R$ {{ item.precoUnitario }}
                </p>
              </div>
              <p class="text-lg font-semibold text-gray-800">
                R$ {{ item.precoUnitario * item.quantidade }}
              </p>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
