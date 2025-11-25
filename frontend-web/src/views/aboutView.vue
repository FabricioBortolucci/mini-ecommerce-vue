<script setup>
import { ref } from 'vue'
import api from '@/services/api'
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()
const resultado = ref(null)
const loading = ref(false)
const status = ref('') // 'success' ou 'error'

const testarSeguranca = async () => {
  loading.value = true
  resultado.value = 'Testando comunicação com o backend...'
  status.value = 'info'

  try {
    // Tenta acessar a rota protegida
    const response = await api.get('/auth/teste')

    resultado.value = `SUCESSO (200 OK): ${response.data}`
    status.value = 'success'
  } catch (error) {
    console.error(error)
    if (error.response && error.response.status === 403) {
      resultado.value = 'ERRO (403 Forbidden): O Backend bloqueou você. Você precisa estar logado!'
    } else if (error.response && error.response.status === 401) {
      resultado.value = 'ERRO (401 Unauthorized): Token inválido ou expirado.'
    } else {
      resultado.value = 'ERRO DE CONEXÃO: O Backend está rodando?'
    }
    status.value = 'error'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="max-w-3xl mx-auto mt-10 p-6">
    <h1 class="text-4xl font-bold text-gray-800 mb-4">Sobre o Projeto</h1>
    <p class="text-gray-600 mb-8 text-lg">
      Este projeto é uma demonstração de arquitetura Full Stack segura utilizando
      <span class="font-bold text-green-600">Spring Boot 3</span> e
      <span class="font-bold text-green-500">Vue.js 3</span>.
    </p>

    <div class="bg-white shadow-lg rounded-lg border border-gray-200 overflow-hidden">
      <div class="bg-gray-50 px-6 py-4 border-b border-gray-200">
        <h2 class="text-xl font-bold text-gray-700 flex items-center">
          🛡️ Console de Teste de Segurança
        </h2>
      </div>

      <div class="p-6 space-y-4">
        <div class="flex items-center justify-between bg-gray-100 p-3 rounded">
          <span class="text-gray-700 font-medium">Status no Frontend:</span>
          <span
            :class="
              authStore.isLoggedIn ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
            "
            class="px-3 py-1 rounded-full text-sm font-bold"
          >
            {{ authStore.isLoggedIn ? 'LOGADO' : 'NÃO LOGADO' }}
          </span>
        </div>

        <div v-if="authStore.isLoggedIn" class="text-xs text-gray-500 break-all">
          <strong>Token Atual:</strong> {{ authStore.token.substring(0, 50) }}...
        </div>

        <hr class="border-gray-200" />

        <p class="text-gray-600">
          O botão abaixo tenta acessar a rota <code>GET /api/auth/teste</code> no backend. Esta rota
          possui a proteção <code>.authenticated()</code> no Spring Security.
        </p>

        <button
          @click="testarSeguranca"
          :disabled="loading"
          class="w-full bg-indigo-600 text-white font-bold py-3 px-4 rounded hover:bg-indigo-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {{ loading ? 'Testando...' : 'Testar Acesso Seguro' }}
        </button>

        <div
          v-if="resultado"
          class="p-4 rounded border mt-4 font-mono text-sm"
          :class="{
            'bg-green-50 border-green-200 text-green-800': status === 'success',
            'bg-red-50 border-red-200 text-red-800': status === 'error',
            'bg-blue-50 border-blue-200 text-blue-800': status === 'info',
          }"
        >
          {{ resultado }}
        </div>
      </div>
    </div>
  </div>
</template>
