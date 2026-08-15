# Social Media Platform

一個使用 **Vue.js + Spring Boot + Microsoft SQL Server** 開發的社群媒體平台。

本專案主要練習前後端分離架構、REST API、使用者登入驗證、文章 CRUD、留言功能，以及 Stored Procedure 資料庫操作。

https://socilal-media-system.vercel.app/ 入口由此進入
---

## 📸 Screenshots

### Login

> 登入頁面截圖放這裡

<img width="1223" height="632" alt="theme" src="https://github.com/user-attachments/assets/9c592c98-5288-4baf-958a-b9a88c5cbe57" />



### Home

>  文章列表截圖放這裡
<img width="1205" height="628" alt="login" src="https://github.com/user-attachments/assets/5670acb2-5dfa-499c-a265-ab7ea433fe49" />


### Post

> 資料庫節圖放這裡


<img width="884" height="553" alt="database" src="https://github.com/user-attachments/assets/f14d4c63-bc3a-4790-a9cd-f47e0145d543" />



---

## ✨ Features

### Authentication

* 使用者註冊
* 使用者登入
* 密碼驗證
* 登出
* 使用者權限判斷

### Posts

* 查看所有文章
* 發表文章
* 修改自己的文章
* 刪除自己的文章
* 文章圖片網址
* 文章建立時間

### Comments

* 查看文章留言
* 新增留言
* 顯示留言者
* 顯示留言時間

### Authorization

使用者只能修改或刪除自己的文章。

---

## 🛠️ Tech Stack

### Frontend

* Vue 3
* Vue Router
* Pinia
* Axios
* Vite

### Backend

* Java
* Spring Boot
* Spring JDBC
* Maven

### Database

* Microsoft SQL Server
* Stored Procedure

---

## 🏗️ Project Structure

```text
social-media-system/
│
├── frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── router/
│   │   ├── stores/
│   │   ├── views/
│   │   └── components/
│   │
│   ├── package.json
│   └── vite.config.js
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │
│   ├── pom.xml
│   └── ...
│
└── README.md
```

---

## 🔌 API

目前主要 API：

| Method | Endpoint                       | Description |
| ------ | ------------------------------ | ----------- |
| POST   | `/api/auth/register`           | 使用者註冊       |
| POST   | `/api/auth/login`              | 使用者登入       |
| GET    | `/api/posts`                   | 取得文章        |
| POST   | `/api/posts`                   | 發表文章        |
| PUT    | `/api/posts/{postId}`          | 修改文章        |
| DELETE | `/api/posts/{postId}`          | 刪除文章        |
| GET    | `/api/posts/{postId}/comments` | 取得文章留言      |
| POST   | `/api/posts/{postId}/comments` | 新增留言        |

---

## 🗄️ Database

本專案使用 Microsoft SQL Server。

資料庫主要包含：

* Users
* Posts
* Comments

部分資料庫操作透過 Stored Procedure 完成，例如：

```text
sp_CreateUser
sp_CreatePost
sp_UpdatePost
sp_DeletePost
sp_CreateComment
sp_GetCommentsByPostId
```

---

## 🚀 Getting Started

### Backend

進入 backend：

```bash
cd backend
```

啟動 Spring Boot：

```bash
mvn spring-boot:run
```

Backend 預設：

```text
http://localhost:8080
```

---

### Frontend

進入 frontend：

```bash
cd frontend
```

安裝套件：

```bash
npm install
```

啟動 Vue：

```bash
npm run dev
```

Frontend 預設：

```text
http://localhost:5174
```

---

## ⚙️ Environment

啟動 Backend 前，需要設定 SQL Server 資料庫連線資訊。

請不要將實際的：

* Database Password
* API Secret
* JWT Secret
* 其他敏感資訊

直接提交到 GitHub。

建議使用環境變數管理敏感設定。

---

## 📚 What I Learned

透過這個專案，我練習了：

* Vue 3 前端開發
* Vue Router 頁面路由
* Pinia 狀態管理
* Axios API 串接
* Spring Boot REST API
* Spring JDBC
* SQL Server
* Stored Procedure
* 前後端分離
* CRUD 操作
* 使用者權限驗證
* Git / GitHub 專案管理

---

## 📌 Project Status

目前已完成：

* [x] 使用者註冊
* [x] 使用者登入
* [x] 文章列表
* [x] 發表文章
* [x] 修改文章
* [x] 刪除文章
* [x] 新增留言
* [x] 查看留言
* [ ] 個人資料
* [ ] 個人資料修改
* [ ] 圖片上傳
* [ ] 線上部署

---

