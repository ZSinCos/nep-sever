<template>
  <div class="auth-page login-page">
    <!-- 顶部：头图 + 标题 -->
    <div class="auth-top login-top">
      <div class="auth-hero login-hero"></div>

      <div class="login-title">
        <h1>东软环保公众监督平台</h1>
        <div class="login-subtitle">
          <span class="line"></span>
          <span class="text">网格员端</span>
          <span class="line"></span>
        </div>
      </div>
    </div>

    <!-- 底部：登录卡片 -->
    <div class="auth-bottom login-bottom">
      <div class="auth-card login-card">
        <div class="auth-input-item">
          <i class="fa fa-user-o"></i>
          <input
              type="text"
              v-model="gmId"
              placeholder="输入网格员编码"
          />
        </div>

        <div class="auth-input-item">
          <i class="fa fa-lock"></i>
          <input
              type="password"
              v-model="gmPassword"
              placeholder="输入登录密码"
          />
        </div>

        <button class="auth-btn login-btn" @click="login">登录</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { setSessionStorage } from '../common';
import '../assets/auth.css';

const axios = inject('axios');
const router = useRouter();

const gmId = ref('');
const gmPassword = ref('');

const login = async () => {
  if (!gmId.value) {
    alert('请输入网格员编码');
    return;
  }
  if (!gmPassword.value) {
    alert('请输入密码');
    return;
  }

  try {
    const res = await axios.get('/gridMember/getGridMemberByCodeByPass', {
      params: {
        gmId: gmId.value,
        gmPassword: gmPassword.value
      }
    });

    if (res.data.code === 200) {
      setSessionStorage('gridMember', res.data.data);
      router.push('/feedbackList');
    } else {
      alert(res.data.message || '登录失败');
    }
  } catch (error) {
    console.error(error);
    alert('登录失败，请重试');
  }
};
</script>

<style scoped>

/* 顶部背景图 */
.login-hero {
  background-image: url('../assets/login-banner.png');
  height: clamp(180px, 32vh, 280px);
}

/* 标题区域 */
.login-title {
  margin-top: 20px;
  padding: 30px 20px 0;
  text-align: center;
}

.login-title h1 {
  margin: 0;
  font-size: 28px;
  font-weight: 700;
  color: #1fb05a;
  line-height: 1.2;
}

.login-subtitle {
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #7ea4a8;
  font-size: 15px;
}

.login-subtitle .line {
  width: 82px;
  height: 1px;
  background: #95b9bb;
}

.login-subtitle .text {
  margin: 0 14px;
  white-space: nowrap;
}


/* 卡片最小高度 */
.login-card {
  min-height: 250px;
  margin-top: 20px;
}
/* 输入框容器 */
.auth-input-item {
  display: flex;
  align-items: center;
  min-height: 55px;
  padding: 0 20px;
  box-sizing: border-box;
  margin-bottom: 20px;
}

.auth-btn {
  margin-top: 15px;
  font-weight: bold;
}


/* ===== 响应式适配 ===== */

/* 大屏/平板 */
@media (min-width: 768px) {
  .login-hero {
    height: 300px;
  }

  .login-title h1 {
    font-size: 34px;
  }

  .login-card {
    min-height: 300px;
  }
}
</style>
