import { ref } from 'vue'
import { defineStore } from 'pinia'

let nextId = 0

export const useNotificationStore = defineStore('notification', () => {
  const notifications = ref([])

  function addNotification(message, type = '', duracao = 3000) {
    const id = nextId++

    notifications.value.push({ id, message, type })

    setTimeout(() => {
      removeNotification(id)
    }, duracao)
  }

  function removeNotification(id) {
    // filtra removendo apenas o id seleciona para remoção
    notifications.value = notifications.value.filter((notification) => notification.id !== id)
  }
  return { notifications, addNotification, removeNotification }
})
