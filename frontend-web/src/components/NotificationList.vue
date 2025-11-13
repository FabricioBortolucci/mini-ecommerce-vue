<script setup>
import { useNotificationStore } from '../stores/notificationStore'

const store = useNotificationStore()
</script>

<template>
  <div class="fixed top-5 right-5 z-50 space-y-3">
    <div
      v-for="notification in store.notifications"
      :key="notification.id"
      class="p-4 rounded-lg shadow-lg text-white"
      :class="{
        'bg-green-500': notification.type === 'success',
        'bg-yellow-300': notification.type === 'warn',
        'bg-red-500': notification.type === 'error',
      }"
    >
      {{ notification.message }}

      <button @click="store.removeNotification(notification.id)" class="ml-4 font-bold">
        &times;
      </button>
    </div>
  </div>
</template>
