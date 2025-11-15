<script setup>
import { useCarrinhoStore } from '../stores/carrinhoStore'

const carrinho = useCarrinhoStore()
</script>

<template>
  <div class="max-w-3xl mx-auto">
    <h1 class="text-3xl font-bold mb-6">Meu Carrinho</h1>

    <div v-if="carrinho.totalItens > 0">
      <ul class="divide-y divide-gray-200 bg-white shadow-lg rounded-lg">
        <li
          v-for="item in carrinho.itens.values()"
          :key="item.id"
          class="flex items-center justify-between p-4"
        >
          <div>
            <h2 class="font-semibold">{{ item.produto.nome }}</h2>
            <p class="text-gray-600">R$ {{ item.produto.preco }}</p>
            <p class="text-gray-600">
              Quantidade:

              <button
                @click="carrinho.decrementarQuantidade(item.produto.id)"
                class="text-gray-700 hover:text-gray-700 font-semibold cursor-pointer"
              >
                -
              </button>

              {{ item.quantidade }}

              <button
                @click="carrinho.incrementarQuantidade(item.produto.id)"
                class="text-blue-500 hover:text-blue-700 font-semibold cursor-pointer"
              >
                +
              </button>
            </p>
          </div>
          <button
            @click="carrinho.remover(item.produto.id)"
            class="text-red-500 hover:text-red-700 font-semibold cursor-pointer"
          >
            Remover
          </button>
        </li>
      </ul>

      <div class="mt-6 text-right">
        <h2 class="text-2xl font-bold">Total: R$ {{ carrinho.valorTotal }}</h2>
        <button
          @click="carrinho.finalizarCompra()"
          class="mt-4 bg-green-600 text-white font-bold py-2 px-6 rounded-lg hover:bg-green-700 cursor-pointer"
        >
          Finalizar Compra
        </button>
      </div>
    </div>

    <div v-else class="text-center text-gray-500 mt-10">
      <p class="text-2xl">Seu carrinho está vazio.</p>
      <RouterLink to="/" class="text-blue-600 hover:underline">Voltar para a loja</RouterLink>
    </div>
  </div>
</template>
