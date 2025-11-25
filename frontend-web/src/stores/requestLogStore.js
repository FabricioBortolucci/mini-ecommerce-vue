import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useRequestLogStore = defineStore('requestLog', () => {
  const logs = ref([])

  function addLog(log) {
    // Adiciona no começo do array (unshift) para o mais recente ficar no topo
    logs.value.unshift({
      id: Date.now() + Math.random(), // ID único
      timestamp: new Date().toLocaleTimeString(),
      ...log
    })

    // Limita a 50 logs para não pesar a memória
    if (logs.value.length > 50) {
      logs.value.pop()
    }
  }

  function clearLogs() {
    logs.value = []
  }

  return { logs, addLog, clearLogs }
})
