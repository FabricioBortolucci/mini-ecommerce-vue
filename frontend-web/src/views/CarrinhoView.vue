<script setup>
import { useCarrinhoStore } from '../stores/carrinhoStore'

const carrinho = useCarrinhoStore()
</script>

<template>
  <div class="max-w-3xl mx-auto">
    <h1 class="text-3xl font-bold mb-6">Meu Carrinho</h1>

    <div v_if="carrinho.totalItens > 0">
      <ul class="divide-y divide-gray-200 bg-white shadow-lg rounded-lg">
        <li
          v-for="item in carrinho.itens"
          :key="item.id"
          class="flex items-center justify-between p-4"
        >
          <div>
            <h2 class="font-semibold">{{ item.nome }}</h2>
            <p class="text-gray-600">R$ {{ item.preco }}</p>
          </div>
          <button
            @click="carrinho.remover(item.id)"
            class="text-red-500 hover:text-red-700 font-semibold cursor-pointer"
          >
            Remover
          </button>
        </li>
      </ul>

      <div class="mt-6 text-right">
        <h2 class="text-2xl font-bold">Total: R$ {{ carrinho.valorTotal }}</h2>
        <button
          class="mt-4 bg-green-600 text-white font-bold py-2 px-6 rounded-lg hover:bg-green-700 cursor-pointer"
        >
          Finalizar Compra (Em breve!)
        </button>
      </div>
    </div>

    <div else class="text-center text-gray-500 mt-10">
      <p class="text-2xl">Seu carrinho está vazio.</p>
      <RouterLink to="/" class="text-blue-600 hover:underline">Voltar para a loja</RouterLink>
    </div>
  </div>
</template>
