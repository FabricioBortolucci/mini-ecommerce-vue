<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header class="bg-gray-800 text-white shadow-md">
    <nav class="container mx-auto p-4 flex justify-between">
      <div class="space-x-6">
        <RouterLink to="/" class="text-xl font-bold hover:text-gray-300">Minha Loja</RouterLink>
        <RouterLink to="/about" class="hover:text-gray-300">Sobre</RouterLink>
      </div>
    </nav>
  </header>

  <main class="container mx-auto p-4">
    <RouterView />
  </main>
</template>

<style scoped></style>
