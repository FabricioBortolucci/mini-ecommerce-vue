import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import api from '@/services/api'
import router from '../router'
import { useNotificationStore } from './notificationStore'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || null)
  const role = ref(localStorage.getItem('role') || null)

  const isLoggedIn = computed(() => token.value !== null)
  const isAdmin = computed(() => role.value === 'ADMIN')
  const notificationStore = useNotificationStore()

  async function login(loginData) {
    try {
      const reponse = await api.post(`/auth/login`, loginData)

      const newToken = reponse.data.token
      const newRole = reponse.data.role

      token.value = newToken
      localStorage.setItem('token', newToken)

      role.value = newRole
      localStorage.setItem('role', newRole)

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
      const newRole = reponse.data.role

      token.value = newToken
      localStorage.setItem('token', newToken)

      role.value = newRole
      localStorage.setItem('role', newRole)

      await router.push('/')
    } catch (error) {
      console.error('Erro no registro', error)
      notificationStore.addNotification('Erro ao registrar', 'error')
    }
  }

  async function logout() {
    token.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('role')

    await router.push('/login')
  }
  return { token, role, isLoggedIn, isAdmin, login, register, logout }
})
