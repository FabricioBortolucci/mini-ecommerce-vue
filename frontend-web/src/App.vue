<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useCarrinhoStore } from './stores/carrinhoStore'

const carrinho = useCarrinhoStore()
</script>

<template>
  <header class="bg-gray-800 text-white shadow-md">
    <nav class="container mx-auto p-4 flex justify-between items-center">
      <div class="space-x-4 flex items-center">
        <RouterLink to="/" class="hover:text-gray-300">Home</RouterLink>

        <RouterLink to="/carrinho" class="relative hover">
          <span class="text-2xl">🛒</span>
          <span
            class="absolute -top-2 -right-2 bg-red-500 text-white rounded-full text-xs w-5 h-5 flex items-center justify-center"
          >
            {{ carrinho.totalItens }}
          </span>
        </RouterLink>
      </div>
    </nav>
  </header>

  <main class="container mx-auto p-4">
    <RouterView />
  </main>
</template>

<style scoped></style>
