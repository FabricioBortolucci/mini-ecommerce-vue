<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useCarrinhoStore } from './stores/carrinhoStore'
import { useAuthStore } from './stores/authStore'
import NotificationList from './components/NotificationList.vue'
import SystemMonitor from '@/components/SystemMonitor.vue'

const carrinho = useCarrinhoStore()
const authStore = useAuthStore()

const handleLogout = () => {
  authStore.logout()
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 flex flex-col">
    <header class="bg-slate-900 text-white shadow-lg sticky top-0 z-40">
      <nav class="container mx-auto px-4 h-16 flex justify-between items-center">
        <div class="flex items-center space-x-6">
          <RouterLink to="/" class="flex items-center gap-2 group">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-8 h-8 text-blue-500 group-hover:text-blue-400 transition-colors"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M13.5 21v-7.5a.75.75 0 0 1 .75-.75h3a.75.75 0 0 1 .75.75V21m-4.5 0H2.36m11.14 0H18m0 0h3.64m-1.39 0V9.349M3.75 21V9.349m0 0a3.001 3.001 0 0 0 3.75-.615A2.993 2.993 0 0 0 9.75 9.75c.896 0 1.7-.393 2.25-1.016a2.993 2.993 0 0 0 2.25 1.016c.896 0 1.7-.393 2.25-1.015a3.001 3.001 0 0 0 3.75.614m-16.5 0a3.004 3.004 0 0 1-.621-4.72l1.189-1.19A1.5 1.5 0 0 1 5.378 3h13.243a1.5 1.5 0 0 1 1.06.44l1.19 1.189a3 3 0 0 1-.621 4.72m-13.5 8.65h3.75a.75.75 0 0 0 .75-.75V13.5a.75.75 0 0 0-.75-.75H6.75a.75.75 0 0 0-.75.75v3.75c0 .414.336.75.75.75Z"
              />
            </svg>
            <span
              class="text-xl font-bold tracking-wide group-hover:text-gray-200 transition-colors"
            >
              Minha Loja
            </span>
          </RouterLink>

          <div class="hidden md:flex space-x-4 ml-4">
            <RouterLink
              to="/"
              class="text-sm font-medium text-gray-300 hover:text-white transition-colors"
            >
              Home
            </RouterLink>
            <RouterLink
              to="/about"
              class="text-sm font-medium text-gray-300 hover:text-white transition-colors"
            >
              Sobre
            </RouterLink>
          </div>
        </div>

        <div class="flex items-center space-x-6">
          <RouterLink
            v-if="authStore.isAdmin"
            to="/admin/produtos"
            class="hidden md:flex items-center gap-1 text-yellow-400 border border-yellow-400 px-3 py-1 rounded-full text-xs font-bold hover:bg-yellow-400 hover:text-gray-900 transition-all"
          >
            <span>⚡ Painel Admin</span>
          </RouterLink>

          <RouterLink
            v-if="authStore.isLoggedIn"
            to="/meus-pedidos"
            class="text-sm font-medium text-gray-300 hover:text-white transition-colors"
          >
            Meus Pedidos
          </RouterLink>

          <div class="flex items-center space-x-4 border-l border-gray-700 pl-6">
            <template v-if="!authStore.isLoggedIn">
              <RouterLink
                to="/login"
                class="text-sm font-medium hover:text-blue-400 transition-colors"
              >
                Login
              </RouterLink>
              <RouterLink
                to="/registro"
                class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
              >
                Registrar
              </RouterLink>
            </template>

            <template v-else>
              <button
                @click="handleLogout"
                class="text-sm font-medium text-red-400 hover:text-red-300 transition-colors flex items-center gap-1"
              >
                <span>Sair</span>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-4 h-4"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9"
                  />
                </svg>
              </button>
            </template>
          </div>

          <RouterLink to="/carrinho" class="relative group">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-7 h-7 text-gray-300 group-hover:text-white transition-colors"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 0 0-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 0 0-16.536-1.84M7.5 14.25 5.106 5.272M6 20.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Zm12.75 0a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z"
              />
            </svg>

            <span
              v-if="carrinho.totalItens > 0"
              class="absolute -top-2 -right-2 bg-red-500 text-white rounded-full text-xs font-bold w-5 h-5 flex items-center justify-center border-2 border-slate-900 animate-pulse-once"
            >
              {{ carrinho.totalItens }}
            </span>
          </RouterLink>
        </div>
      </nav>
    </header>

    <main class="container mx-auto p-4 flex-grow">
      <RouterLink
        v-if="authStore.isAdmin"
        to="/admin/produtos"
        class="md:hidden mb-4 block text-center text-yellow-600 font-bold border border-yellow-600 p-2 rounded"
      >
        Acessar Painel Admin
      </RouterLink>

      <RouterView v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </RouterView>
    </main>

    <NotificationList />
    <SystemMonitor />

    <footer class="bg-slate-900 text-gray-400 py-6 mt-8">
      <div class="container mx-auto text-center text-sm">
        &copy; 2025 Minha Loja Full Stack. Desenvolvido com Vue 3 e Spring Boot.
      </div>
    </footer>
  </div>
</template>

<style scoped>
/* Animação suave para troca de páginas */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* Animação simples para o badge do carrinho quando atualiza */
@keyframes pulse-scale {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}
.animate-pulse-once {
  animation: pulse-scale 0.3s ease-in-out;
}
</style>
