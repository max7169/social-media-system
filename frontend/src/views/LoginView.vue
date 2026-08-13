<template>
  <div class="login-page">
    <div class="login-card">
      <h1>Social Media</h1>
      <h2>登入</h2>

      <form @submit.prevent="login">
        <div class="form-group">
          <label>手機號碼</label>
          <input
            v-model="phone"
            type="text"
            placeholder="請輸入手機號碼"
            required
          />
        </div>

        <div class="form-group">
          <label>密碼</label>
          <input
            v-model="password"
            type="password"
            placeholder="請輸入密碼"
            required
          />
        </div>

        <p v-if="errorMessage" class="error">
          {{ errorMessage }}
        </p>

        <button type="submit" :disabled="loading">
          {{ loading ? '登入中...' : '登入' }}
        </button>
      </form>

      <p class="register-link">
        還沒有帳號？
        <RouterLink to="/register">註冊</RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import api from '../api/api'

const router = useRouter()
const authStore = useAuthStore()

const phone = ref('')
const password = ref('')
const loading = ref(false)
const errorMessage = ref('')

const login = async () => {
  loading.value = true
  errorMessage.value = ''

  try {
    const response = await api.post('/api/auth/login', {
      phone: phone.value,
      password: password.value
    })
authStore.setUser(response.data)

    console.log('登入成功:', response.data)

alert('登入成功！')
    // 登入成功後先回首頁
    router.push('/home')
  } catch (error) {
    console.error(error)

    if (error.response) {
      errorMessage.value =
        error.response.data?.message || '登入失敗'
    } else {
      errorMessage.value = '無法連線到伺服器'
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f5f5;
}

.login-card {
  width: 380px;
  padding: 40px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 10px;
}

h2 {
  text-align: center;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 6px;
}

input {
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

button {
  width: 100%;
  padding: 11px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
}

.error {
  color: #d32f2f;
}

.register-link {
  text-align: center;
  margin-top: 20px;
}
</style>