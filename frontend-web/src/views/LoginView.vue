<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../stores/authStore'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const username = ref('')
const password = ref('')
const loading = ref(false)

const handleSubmit = async () => {
  loading.value = true
  await authStore.login({
    username: username.value,
    password: password.value,
  })
  loading.value = false
}


const loginDemoAdmin = async () => {
  loading.value = true
  await authStore.login({
    username: 'admin',
    password: '442612',
  })
  loading.value = false
}
</script>

<template>
  <div class="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
    <h1 class="text-3xl font-bold text-center mb-6 text-gray-800">Acesso</h1>

    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div>
        <label class="block text-gray-700 font-bold mb-2">Email</label>
        <input
          v-model="username"
          type="text"
          class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          required
        />
      </div>
      <div>
        <label class="block text-gray-700 font-bold mb-2">Senha</label>
        <input
          v-model="password"
          type="password"
          class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
          required
        />
      </div>

      <button
        type="submit"
        :disabled="loading"
        class="w-full bg-blue-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-blue-700 disabled:opacity-50"
      >
        {{ loading ? 'Entrando...' : 'Entrar' }}
      </button>
    </form>

    <div class="relative my-6">
      <div class="absolute inset-0 flex items-center">
        <div class="w-full border-t border-gray-300"></div>
      </div>
      <div class="relative flex justify-center text-sm">
        <span class="px-2 bg-white text-gray-500">Área de Demonstração</span>
      </div>
    </div>

    <button
      @click="loginDemoAdmin"
      :disabled="loading"
      class="w-full bg-gray-800 text-yellow-400 border border-yellow-400 font-bold py-2 px-4 rounded-lg hover:bg-gray-700 transition-colors flex justify-center items-center gap-2"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        fill="currentColor"
        class="w-5 h-5"
      >
        <path
          fill-rule="evenodd"
          d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z"
          clip-rule="evenodd"
        />
      </svg>
      Testar como Admin
    </button>

    <div class="text-center mt-4">
      <p class="text-sm text-gray-600">
        Não tem conta?
        <RouterLink to="/registro" class="text-blue-600 hover:underline">Registre-se</RouterLink>
      </p>
    </div>
  </div>
</template>
