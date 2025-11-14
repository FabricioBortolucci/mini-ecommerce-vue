import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import api from '@/services/api'
import router from '../router'
import { useNotificationStore } from './notificationStore'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || null)

  const isLoggedIn = computed(() => token.value !== null)
  const notificationStore = useNotificationStore()

  async function login(loginData) {
    try {
      const reponse = await api.post(`/auth/login`, loginData)

      const newToken = reponse.data.token

      token.value = newToken
      localStorage.setItem('token', newToken)

      await router.push('/')
    } catch (error) {
      console.error('erro de login:', error)

      notificationStore.addNotification('Erro ao logar', 'error')
    }
  }

  async function register(registerData) {
    try {
      const reponse = await api.post(`/auth/register`, registerData)
      const newToken = reponse.data.token

      token.value = newToken
      localStorage.setItem('token', newToken)

      await router.push('/')
    } catch (error) {
      console.error('Erro no registro', error)
      notificationStore.addNotification('Erro ao registrar', 'error')
    }
  }

  async function logout() {
    token.value = null
    localStorage.removeItem('token')

    await router.push('/login')
  }
  return { token, isLoggedIn, login, register, logout }
})
