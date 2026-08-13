import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)

  const setUser = (userData) => {
    user.value = userData
  }

  const clearUser = () => {
    user.value = null
  }

  const isLoggedIn = () => {
    return user.value !== null
  }

  return {
    user,
    setUser,
    clearUser,
    isLoggedIn
  }
})