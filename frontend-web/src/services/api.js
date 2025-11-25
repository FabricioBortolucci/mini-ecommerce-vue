import axios from 'axios'
import { useAuthStore } from '../stores/authStore'
import { useRequestLogStore } from '../stores/requestLogStore'
import { createPinia } from 'pinia'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api'
});

api.interceptors.request.use(config => {
  const authStore = useAuthStore()
  const token = authStore.token
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }

  config.metadata = { startTime: new Date() }

  return config
}, error => {
  return Promise.reject(error)
});

// --- INTERCEPTOR DE RESPOSTA (CHEGADA) ---
api.interceptors.response.use(
  (response) => {
    // Sucesso (200, 201, etc)
    logResponse(response, 'success')
    return response
  },
  (error) => {
    // Erro (400, 403, 500)
    logResponse(error.response || error, 'error')
    return Promise.reject(error)
  }
);

function logResponse(res, statusType) {
  const logStore = useRequestLogStore()

  const config = res.config || res // Em caso de erro de rede grave
  const startTime = config.metadata?.startTime || new Date()
  const duration = new Date() - startTime

  logStore.addLog({
    method: config.method ? config.method.toUpperCase() : 'UNKNOWN',
    url: config.url,
    status: res.status || 'ERR',
    statusText: res.statusText || 'Network Error',
    duration: duration,
    type: statusType // 'success' ou 'error'
  })
}

export default api
