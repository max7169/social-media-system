<template>
  <div class="home">

   <header class="header">

  <h1>Social Media</h1>

  <div class="header-actions">

    <button @click="goProfile">
      個人資料
    </button>

    <button @click="logout">
      登出
    </button>

  </div>

    </header>

    <main class="content">

      <!-- =========================
           發表文章
      ========================== -->

      <div class="create-post">

        <h3>發表文章</h3>

        <textarea
          v-model="content"
          placeholder="你現在在想什麼？"
          rows="5"
        ></textarea>

        <input
          v-model="image"
          type="text"
          placeholder="圖片網址（可不填）"
        />

        <p
          v-if="createPostError"
          class="error"
        >
          {{ createPostError }}
        </p>

        <button
          @click="createPost"
          :disabled="creatingPost"
        >
          {{ creatingPost ? '發布中...' : '發布' }}
        </button>

      </div>


      <!-- =========================
           最新文章
      ========================== -->

      <h2>最新文章</h2>


      <!-- Loading -->

      <p v-if="loading">
        載入文章中...
      </p>


      <!-- Error -->

      <p
        v-else-if="errorMessage"
        class="error"
      >
        {{ errorMessage }}
      </p>


      <!-- 沒有文章 -->

      <p
        v-else-if="posts.length === 0"
      >
        目前還沒有文章
      </p>


      <!-- =========================
           文章列表
      ========================== -->

      <div
        v-for="post in posts"
        :key="post.postId"
        class="post-card"
      >

        <!-- 作者 -->

        <div class="post-header">
          <strong>
            {{ post.userName }}
          </strong>
        </div>


        <!-- =========================
             編輯模式
        ========================== -->

        <div
          v-if="editingPostId === post.postId"
          class="edit-area"
        >

          <textarea
            v-model="editContent"
            rows="5"
          ></textarea>

          <input
            v-model="editImage"
            type="text"
            placeholder="圖片網址"
          />

          <div class="edit-actions">

            <button
              @click="updatePost"
            >
              儲存
            </button>

            <button
              @click="cancelEdit"
            >
              取消
            </button>

          </div>

        </div>


        <!-- =========================
             一般文章
        ========================== -->

        <div v-else>

          <p class="post-content">
            {{ post.content }}
          </p>


          <!-- 圖片 -->

          <div
            v-if="post.image"
            class="post-image"
          >
            <img
              :src="post.image"
              alt="文章圖片"
            />
          </div>

        </div>


        <!-- =========================
             文章資訊
        ========================== -->

        <div class="post-info">

          <span>
            {{ post.createdAt }}
          </span>

        </div>


        <!-- =========================
             修改 / 刪除
        ========================== -->

        <div
          v-if="
            authStore.user?.userId === post.userId
          "
          class="post-actions"
        >

          <button
            @click="startEdit(post)"
          >
            修改
          </button>

          <button
            @click="deletePost(post.postId)"
          >
            刪除
          </button>

        </div>


        <!-- =========================
             留言
        ========================== -->

        <div class="comments">

          <!-- 查看留言 -->

          <button
            class="load-comments-button"
            @click="loadComments(post.postId)"
          >
            查看留言
          </button>


          <!-- 留言 Loading -->

          <p
            v-if="loadingComments[post.postId]"
            class="comment-loading"
          >
            載入留言中...
          </p>


          <!-- 留言錯誤 -->

          <p
            v-if="commentErrors[post.postId]"
            class="error"
          >
            {{ commentErrors[post.postId] }}
          </p>


          <!-- =========================
               留言列表
          ========================== -->

          <div
            v-if="comments[post.postId]"
            class="comment-list"
          >

            <p
              v-if="
                comments[post.postId].length === 0
              "
              class="no-comments"
            >
              目前沒有留言
            </p>


            <div
              v-for="comment in comments[post.postId]"
              :key="comment.commentId"
              class="comment"
            >

              <div class="comment-user">

                <strong>
                  {{ comment.userName }}
                </strong>

              </div>


              <div class="comment-content">

                {{ comment.content }}

              </div>


              <small class="comment-time">

                {{ comment.createdAt }}

              </small>

            </div>

          </div>


          <!-- =========================
               新增留言
          ========================== -->

          <div class="comment-form">

            <input
              v-model="commentContent[post.postId]"
              type="text"
              placeholder="輸入留言..."
              @keyup.enter="
                createComment(post.postId)
              "
            />

            <button
              @click="
                createComment(post.postId)
              "
            >
              送出
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

import { useRouter } from 'vue-router'

import { useAuthStore } from '../stores/auth'

import api from '../api/api'


const router = useRouter()

const authStore = useAuthStore()


/* =========================
   文章列表
========================= */

const posts = ref([])

const loading = ref(true)

const errorMessage = ref('')


/* =========================
   發文
========================= */

const content = ref('')

const image = ref('')

const creatingPost = ref(false)

const createPostError = ref('')


/* =========================
   修改文章
========================= */

const editingPostId = ref(null)

const editContent = ref('')

const editImage = ref('')


/* =========================
   留言
========================= */

const comments = ref({})

const commentContent = ref({})

const loadingComments = ref({})

const commentErrors = ref({})


/* =========================
   取得文章
========================= */

const loadPosts = async () => {

  try {

    loading.value = true

    errorMessage.value = ''

    const response =
      await api.get('/api/posts')

    console.log(
      '文章資料:',
      response.data
    )

    posts.value = response.data

  } catch (error) {

    console.error(error)

    errorMessage.value =
      '無法取得文章'

  } finally {

    loading.value = false

  }

}


/* =========================
   發表文章
========================= */

const createPost = async () => {

  if (!content.value.trim()) {

    createPostError.value =
      '文章內容不能為空'

    return

  }


  if (!authStore.user) {

    createPostError.value =
      '請先登入'

    return

  }


  creatingPost.value = true

  createPostError.value = ''


  try {

    const response =
      await api.post(
        '/api/posts',
        {
          userId:
            authStore.user.userId,

          content:
            content.value,

          image:
            image.value || null
        }
      )


    console.log(
      '發文成功:',
      response.data
    )


    // 清空表單

    content.value = ''

    image.value = ''


    // 重新取得文章

    await loadPosts()


  } catch (error) {

    console.error(error)

    createPostError.value =
      error.response?.data?.message ||
      '發文失敗'

  } finally {

    creatingPost.value = false

  }

}


/* =========================
   開始修改
========================= */

const startEdit = (post) => {

  editingPostId.value =
    post.postId

  editContent.value =
    post.content

  editImage.value =
    post.image || ''

}


/* =========================
   取消修改
========================= */

const cancelEdit = () => {

  editingPostId.value = null

  editContent.value = ''

  editImage.value = ''

}


/* =========================
   修改文章
========================= */

const updatePost = async () => {

  if (!editContent.value.trim()) {

    alert(
      '文章內容不能為空'
    )

    return

  }


  if (!authStore.user) {

    alert(
      '請先登入'
    )

    return

  }


  try {

    const response =
      await api.put(
        `/api/posts/${editingPostId.value}`,
        {
          userId:
            authStore.user.userId,

          content:
            editContent.value,

          image:
            editImage.value || null
        }
      )


    console.log(
      '修改成功:',
      response.data
    )


    cancelEdit()

    await loadPosts()


  } catch (error) {

    console.error(error)

    alert(
      error.response?.data?.message ||
      '修改文章失敗'
    )

  }

}


/* =========================
   刪除文章
========================= */

const deletePost = async (postId) => {

  const confirmed =
    confirm(
      '確定要刪除這篇文章嗎？'
    )


  if (!confirmed) {

    return

  }


  if (!authStore.user) {

    alert(
      '請先登入'
    )

    return

  }


  try {

    const response =
      await api.delete(
        `/api/posts/${postId}`,
        {
          data: {
            userId:
              authStore.user.userId
          }
        }
      )


    console.log(
      '刪除成功:',
      response.data
    )


    await loadPosts()


  } catch (error) {

    console.error(error)

    alert(
      error.response?.data?.message ||
      '刪除文章失敗'
    )

  }

}


/* =========================
   取得留言
========================= */

const loadComments = async (postId) => {

  try {

    loadingComments.value[postId] =
      true

    commentErrors.value[postId] =
      ''


    const response =
      await api.get(
        `/api/posts/${postId}/comments`
      )


    console.log(
      `文章 ${postId} 的留言:`,
      response.data
    )


    comments.value[postId] =
      response.data


  } catch (error) {

    console.error(error)

    commentErrors.value[postId] =
      error.response?.data?.message ||
      '留言載入失敗'


  } finally {

    loadingComments.value[postId] =
      false

  }

}


/* =========================
   新增留言
========================= */

const createComment = async (postId) => {

  if (!authStore.user) {

    alert(
      '請先登入'
    )

    return

  }


  const text =
    commentContent.value[postId]


  if (!text || !text.trim()) {

    return

  }


  try {

    await api.post(
      `/api/posts/${postId}/comments`,
      {
        userId:
          authStore.user.userId,

        content:
          text.trim()
      }
    )


    // 清空留言輸入框

    commentContent.value[postId] =
      ''


    // 重新取得留言

    await loadComments(postId)


  } catch (error) {

    console.error(error)

    alert(
      error.response?.data?.message ||
      '留言失敗'
    )

  }

}


/* =========================
   登出
========================= */

const logout = () => {

  authStore.clearUser()

  router.push('/')

}


/* =========================
   頁面載入
========================= */

onMounted(() => {

  loadPosts()

})

const goProfile = () => {
  router.push('/profile')
}

</script>


<style scoped>

.home {

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

.header button {

  padding: 8px 16px;

  cursor: pointer;

}


/* =========================
   Content
========================= */

.content {

  width: 700px;

  max-width:
    calc(100% - 40px);

  margin: 30px auto;

}


/* =========================
   發文
========================= */

.create-post {

  background: white;

  padding: 20px;

  margin-bottom: 30px;

  border-radius: 10px;

  box-shadow:
    0 2px 8px
    rgba(0, 0, 0, 0.08);

}

.create-post h3 {

  margin-top: 0;

}

.create-post textarea {

  width: 100%;

  box-sizing: border-box;

  padding: 12px;

  margin-bottom: 10px;

  resize: vertical;

}

.create-post input {

  width: 100%;

  box-sizing: border-box;

  padding: 10px;

  margin-bottom: 10px;

}

.create-post button {

  padding: 10px 20px;

  cursor: pointer;

}


/* =========================
   文章
========================= */

.post-card {

  background: white;

  padding: 20px;

  margin-bottom: 20px;

  border-radius: 10px;

  box-shadow:
    0 2px 8px
    rgba(0, 0, 0, 0.08);

}

.post-header {

  margin-bottom: 12px;

}

.post-content {

  font-size: 16px;

  line-height: 1.6;

}


/* =========================
   圖片
========================= */

.post-image img {

  max-width: 100%;

  border-radius: 8px;

}


/* =========================
   文章資訊
========================= */

.post-info {

  margin-top: 15px;

  color: #888;

  font-size: 13px;

}


/* =========================
   修改區域
========================= */

.edit-area textarea {

  width: 100%;

  box-sizing: border-box;

  padding: 10px;

  margin-bottom: 10px;

  resize: vertical;

}

.edit-area input {

  width: 100%;

  box-sizing: border-box;

  padding: 10px;

  margin-bottom: 10px;

}


/* =========================
   修改按鈕
========================= */

.edit-actions {

  display: flex;

  gap: 10px;

}

.edit-actions button {

  padding: 7px 14px;

  cursor: pointer;

}


/* =========================
   文章操作
========================= */

.post-actions {

  display: flex;

  gap: 10px;

  margin-top: 15px;

}

.post-actions button {

  padding: 7px 14px;

  cursor: pointer;

}


/* =========================
   留言區
========================= */

.comments {

  margin-top: 20px;

  padding-top: 15px;

  border-top: 1px solid #eee;

}


/* 查看留言 */

.load-comments-button {

  padding: 7px 12px;

  cursor: pointer;

}


/* 留言 Loading */

.comment-loading {

  margin-top: 10px;

  color: #888;

}


/* 留言列表 */

.comment-list {

  margin-top: 15px;

}


/* 沒有留言 */

.no-comments {

  color: #888;

}


/* 單則留言 */

.comment {

  padding: 10px;

  margin-bottom: 8px;

  background: #f7f7f7;

  border-radius: 6px;

}


/* 留言者 */

.comment-user {

  margin-bottom: 5px;

}


/* 留言內容 */

.comment-content {

  line-height: 1.5;

}


/* 留言時間 */

.comment-time {

  display: block;

  margin-top: 5px;

  color: #888;

}


/* 留言輸入 */

.comment-form {

  display: flex;

  gap: 8px;

  margin-top: 15px;

}

.comment-form input {

  flex: 1;

  padding: 9px;

  border: 1px solid #ccc;

  border-radius: 5px;

}

.comment-form button {

  padding: 8px 15px;

  cursor: pointer;

}

.header-actions {
  display: flex;
  gap: 10px;
}


/* =========================
   錯誤
========================= */

.error {

  color: #d32f2f;

}

</style>