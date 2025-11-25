<script setup>
import { useRequestLogStore } from '@/stores/requestLogStore'
import { ref } from 'vue'

const logStore = useRequestLogStore()
const isOpen = ref(false) // Começa fechado/minimizado

const toggle = () => (isOpen.value = !isOpen.value)

// Cores para os métodos HTTP
const methodColor = (method) => {
  const colors = {
    GET: 'text-blue-400',
    POST: 'text-green-400',
    PUT: 'text-yellow-400',
    DELETE: 'text-red-400',
    ROUTE: 'text-purple-400'
  }
  return colors[method] || 'text-gray-400'
}
</script>

<template>
  <button
    @click="toggle"
    class="fixed bottom-4 left-4 z-50 bg-gray-900 text-green-400 border border-green-500 px-4 py-2 rounded shadow-lg font-mono text-xs hover:bg-gray-800 transition-all"
  >
    {{ isOpen ? '▼ Esconder Monitor' : '▲ Monitor de Rede' }}
  </button>

  <div
    v-if="isOpen"
    class="fixed bottom-16 left-4 z-50 w-full max-w-lg bg-gray-900 bg-opacity-95 text-white border border-gray-700 rounded-lg shadow-2xl overflow-hidden flex flex-col"
    style="height: 300px"
  >
    <div
      class="bg-gray-800 px-4 py-2 flex justify-between items-center text-xs border-b border-gray-700"
    >
      <span class="font-bold text-gray-300">System Traffic Monitor</span>
      <button @click="logStore.clearLogs" class="text-red-400 hover:text-white">Limpar</button>
    </div>

    <div class="flex-1 overflow-y-auto p-2 font-mono text-xs space-y-1 scrollbar-thin">
      <div v-if="logStore.logs.length === 0" class="text-gray-500 text-center mt-10">
        Aguardando tráfego...
      </div>

      <div
        v-for="log in logStore.logs"
        :key="log.id"
        class="flex items-center justify-between p-1 hover:bg-gray-800 rounded"
        :class="log.type === 'error' ? 'border-l-2 border-red-500 bg-red-900/10' : ''"
      >
        <div class="flex items-center space-x-2 overflow-hidden">
          <span class="text-gray-500">[{{ log.timestamp }}]</span>
          <span :class="['font-bold', methodColor(log.method)]">{{ log.method }}</span>
          <span class="truncate text-gray-300" :title="log.url">{{ log.url }}</span>
        </div>

        <div class="flex items-center space-x-2 shrink-0">
          <span :class="log.type === 'success' ? 'text-green-400' : 'text-red-400'">
            {{ log.status }}
          </span>
          <span class="text-gray-500 w-12 text-right">{{ log.duration }}ms</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Estilo opcional para a barra de rolagem ficar bonita */
.scrollbar-thin::-webkit-scrollbar {
  width: 6px;
}
.scrollbar-thin::-webkit-scrollbar-track {
  background: #1f2937;
}
.scrollbar-thin::-webkit-scrollbar-thumb {
  background: #4b5563;
  border-radius: 3px;
}
</style>
