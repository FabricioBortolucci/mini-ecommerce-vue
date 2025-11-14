<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../stores/authStore'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const username = ref('')
const password = ref('')

const handleSubmit = async () => {
  await authStore.login({
    username: username.value,
    password: password.value,
  })
}
</script>

<template>
  <div class="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
    <h1 class="text-3xl font-bold text-center mb-6">Login</h1>

    <form @submit.prevent="handleSubmit">
      <div class="mb-4">
        <label for="username" class="block text-gray-700 font-bold mb-2">Nome:</label>
        <input
          v-model="username"
          type="username"
          id="username"
          class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring focus:border-blue-300"
          required
        />
      </div>
      <div class="mb-6">
        <label for="password" class="block text-gray-700 font-bold mb-2">Senha:</label>
        <input
          v-model="password"
          type="password"
          id="password"
          class="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring focus:border-blue-300"
          required
        />
      </div>
      <button
        type="submit"
        class="w-full bg-blue-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-blue-700"
      >
        Entrar
      </button>
    </form>

    <div class="text-center mt-4">
      <p>
        Não tem uma conta?
        <RouterLink to="/registro" class="text-blue-600 hover:underline">
          Registre-se aqui
        </RouterLink>
      </p>
    </div>
  </div>
</template>
