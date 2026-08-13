<template>
  <div class="profile-page">

    <!-- Header -->
    <header class="header">

      <h1>Social Media</h1>

      <div class="header-actions">

        <button @click="goHome">
          首頁
        </button>

        <button @click="logout">
          登出
        </button>

      </div>

    </header>


    <main class="profile-container">

      <!-- Loading -->
      <p v-if="loading">
        載入個人資料中...
      </p>


      <!-- Error -->
      <p
        v-else-if="errorMessage"
        class="error"
      >
        {{ errorMessage }}
      </p>


      <!-- Profile -->
      <div
        v-else-if="user"
        class="profile-card"
      >

        <!-- Cover -->
        <div
          class="cover"
          :style="{
            backgroundImage: user.coverImage
              ? `url(${user.coverImage})`
              : 'none'
          }"
        >
          <div
            v-if="!user.coverImage"
            class="cover-placeholder"
          >
            Social Media
          </div>
        </div>


        <!-- Avatar -->
        <div class="avatar">
          {{ getAvatarText(user.userName) }}
        </div>


        <!-- User Info -->
        <div class="profile-content">

          <h2>
            {{ user.userName }}
          </h2>


          <div class="info-row">

            <span class="label">
              Email
            </span>

            <span>
              {{ user.email }}
            </span>

          </div>


          <div class="info-row">

            <span class="label">
              電話
            </span>

            <span>
              {{ user.phone }}
            </span>

          </div>


          <div class="biography">

            <h3>
              個人簡介
            </h3>

            <p>
              {{ user.biography || '尚未填寫個人簡介' }}
            </p>

          </div>


          <div class="profile-actions">

            <button
              @click="startEdit"
            >
              編輯個人資料
            </button>

          </div>

        </div>

      </div>

    </main>

  </div>
</template>


<script setup>

import {
  ref,
  onMounted
} from 'vue'

import {
  useRouter
} from 'vue-router'

import {
  useAuthStore
} from '../stores/auth'

import api from '../api/api'


const router = useRouter()

const authStore = useAuthStore()


/* =========================
   狀態
========================= */

const user = ref(null)

const loading = ref(true)

const errorMessage = ref('')


/* =========================
   取得使用者資料
========================= */

const loadProfile = async () => {

  if (!authStore.user) {

    router.push('/login')

    return

  }


  try {

    loading.value = true

    errorMessage.value = ''


    /*
      這裡使用目前登入者的 UserId
    */

    const response =
      await api.get(
        `/api/users/${authStore.user.userId}`
      )


    console.log(
      '個人資料:',
      response.data
    )


    user.value =
      response.data


  } catch (error) {

    console.error(error)

    errorMessage.value =
      error.response?.data?.message ||
      '無法取得個人資料'


  } finally {

    loading.value = false

  }

}


/* =========================
   Avatar
========================= */

const getAvatarText = (name) => {

  if (!name) {

    return '?'

  }

  return name.charAt(0).toUpperCase()

}


/* =========================
   編輯
========================= */

const startEdit = () => {

  alert(
    '個人資料編輯功能下一步製作'
  )

}


/* =========================
   回首頁
========================= */

const goHome = () => {

  router.push('/home')

}


/* =========================
   登出
========================= */

const logout = () => {

  authStore.clearUser()

  router.push('/login')

}


/* =========================
   載入
========================= */

onMounted(() => {

  loadProfile()

})

</script>


<style scoped>

.profile-page {

  min-height: 100vh;

  background: #f5f5f5;

}


/* =========================
   Header
========================= */

.header {

  height: 60px;

  padding: 0 30px;

  background: white;

  display: flex;

  align-items: center;

  justify-content: space-between;

  box-shadow:
    0 1px 5px
    rgba(0, 0, 0, 0.1);

}

.header h1 {

  margin: 0;

}

.header-actions {

  display: flex;

  gap: 10px;

}

.header button {

  padding: 8px 16px;

  cursor: pointer;

}


/* =========================
   Container
========================= */

.profile-container {

  width: 700px;

  max-width:
    calc(100% - 40px);

  margin: 30px auto;

}


/* =========================
   Profile Card
========================= */

.profile-card {

  position: relative;

  background: white;

  border-radius: 12px;

  overflow: hidden;

  box-shadow:
    0 2px 8px
    rgba(0, 0, 0, 0.08);

}


/* =========================
   Cover
========================= */

.cover {

  height: 180px;

  background-size: cover;

  background-position: center;

  background-color: #ddd;

  display: flex;

  align-items: center;

  justify-content: center;

}

.cover-placeholder {

  color: white;

  font-size: 28px;

  font-weight: bold;

}


/* =========================
   Avatar
========================= */

.avatar {

  width: 90px;

  height: 90px;

  margin:
    -45px auto 0;

  border-radius: 50%;

  background: #555;

  color: white;

  display: flex;

  align-items: center;

  justify-content: center;

  font-size: 36px;

  font-weight: bold;

  border: 5px solid white;

  position: relative;

}


/* =========================
   Profile Content
========================= */

.profile-content {

  padding: 20px 30px 30px;

  text-align: center;

}

.profile-content h2 {

  margin-top: 5px;

  margin-bottom: 25px;

}


/* =========================
   User Info
========================= */

.info-row {

  display: flex;

  justify-content: space-between;

  padding: 15px 0;

  border-bottom:
    1px solid #eee;

  text-align: left;

}

.label {

  font-weight: bold;

  color: #555;

}


/* =========================
   Biography
========================= */

.biography {

  margin-top: 25px;

  text-align: left;

}

.biography h3 {

  margin-bottom: 10px;

}

.biography p {

  color: #555;

  line-height: 1.6;

}


/* =========================
   Actions
========================= */

.profile-actions {

  margin-top: 25px;

}

.profile-actions button {

  padding: 10px 20px;

  cursor: pointer;

  border: none;

  border-radius: 6px;

}


/* =========================
   Error
========================= */

.error {

  color: #d32f2f;

}

</style>