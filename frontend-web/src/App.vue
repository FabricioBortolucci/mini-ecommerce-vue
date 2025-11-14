<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useCarrinhoStore } from './stores/carrinhoStore'
import { useAuthStore } from './stores/authStore'
import NotificationList from './components/NotificationList.vue'
import api from './services/api'
const carrinho = useCarrinhoStore()
const authStore = useAuthStore()

const handleLogout = () => {
  authStore.logout()
}
const testarRotaSegura = async () => {
  try {
    const response = await api.get('/auth/teste')
    console.log('Resposta da rota segura:', response.data)
    alert('Sucesso! Você acessou uma rota protegida.')
  } catch (error) {
    console.error('Falhou!', error)
    alert('Erro! Você não tem permissão.')
  }
}
</script>

<template>
  <header class="bg-gray-800 text-white shadow-md">
    <nav class="container mx-auto p-4 flex justify-between items-center">
      <div>
        <RouterLink to="/" class="text-xl font-bold ...">Minha Loja</RouterLink>
      </div>

      <div class="space-x-4 flex items-center">
        <RouterLink to="/" class="hover:text-gray-300">Home</RouterLink>

        <template v-if="!authStore.isLoggedIn">
          <RouterLink to="/login" class="hover:text-gray-300">Login</RouterLink>
          <RouterLink to="/registro" class="hover:text-gray-300">Registrar</RouterLink>
        </template>

        <template v-else>
          <button @click="handleLogout" class="hover:text-gray-300 cursor-pointer">Logout</button>
        </template>

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
    <button @click="testarRotaSegura">Testar Rota Segura</button>

    <RouterView />
  </main>

  <NotificationList />
</template>

<style scoped></style>
