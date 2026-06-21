<template>
  <div class="login-container">
    <div class="login-particles">
      <div v-for="i in 20" :key="i" class="particle" :style="getParticleStyle(i)"></div>
    </div>
    <div class="login-box">
      <div class="login-header">
        <div class="logo-icon">
          <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" fill="#409EFF"/>
          </svg>
        </div>
        <h1>东软环保公众监督系统</h1>
        <p class="subtitle">系统管理端</p>
      </div>
      <el-form :model="form" :rules="rules" ref="loginForm" class="login-form">
        <el-form-item prop="adminId">
          <el-input 
            v-model="form.adminId" 
            placeholder="请输入管理员编码" 
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        <el-form-item prop="adminPassword">
          <el-input 
            v-model="form.adminPassword" 
            type="password" 
            placeholder="请输入密码" 
            prefix-icon="Lock"
            size="large"
            show-password
            @keyup.enter="login"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" style="width: 100%;" @click="login" :loading="loading">
            {{ loading ? '登录中...' : '登 录' }}
          </el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
        <p>默认账号：admin / 123456</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { setSessionStorage } from '../common';

const axios = inject('axios');
const myElMessage = inject('myElMessage');
const router = useRouter();

const loginForm = ref(null);
const loading = ref(false);

const form = ref({
  adminId: '',
  adminPassword: ''
});

const rules = {
  adminId: [{ required: true, message: '请输入管理员编码', trigger: 'blur' }],
  adminPassword: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};

const getParticleStyle = (i) => {
  const size = Math.random() * 10 + 5;
  const left = Math.random() * 100;
  const delay = Math.random() * 5;
  const duration = Math.random() * 10 + 10;
  return {
    width: size + 'px',
    height: size + 'px',
    left: left + '%',
    animationDelay: delay + 's',
    animationDuration: duration + 's'
  };
};

const login = async () => {
  if (!form.value.adminId || !form.value.adminPassword) {
    myElMessage('请输入管理员编码和密码', 'warning');
    return;
  }
  loading.value = true;
  try {
    const res = await axios.get('/admins/getAdminsByCode', {
      params: { adminId: form.value.adminId, adminPassword: form.value.adminPassword }
    });
    if (res.data.code === 200) {
      setSessionStorage('admins', res.data.data);
      myElMessage('登录成功', 'success');
      router.push('/index');
    } else {
      myElMessage(res.data.message, 'error');
    }
  } catch (error) {
    myElMessage('登录失败，请重试', 'error');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.login-particles {
  position: absolute;
  width: 100%;
  height: 100%;
}

.particle {
  position: absolute;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  bottom: -10px;
  animation: float linear infinite;
}

@keyframes float {
  0% {
    transform: translateY(0) rotate(0deg);
    opacity: 1;
  }
  100% {
    transform: translateY(-100vh) rotate(720deg);
    opacity: 0;
  }
}

.login-box {
  width: 420px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(10px);
  z-index: 1;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.logo-icon {
  width: 60px;
  height: 60px;
  margin: 0 auto 15px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px;
}

.logo-icon svg {
  width: 100%;
  height: 100%;
}

.login-header h1 {
  font-size: 24px;
  color: #333;
  margin: 0 0 8px 0;
  font-weight: 600;
}

.subtitle {
  color: #999;
  font-size: 14px;
  margin: 0;
}

.login-form {
  margin-top: 20px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.login-form :deep(.el-button) {
  border-radius: 8px;
  height: 44px;
  font-size: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
}

.login-form :deep(.el-button:hover) {
  opacity: 0.9;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.login-footer p {
  color: #999;
  font-size: 12px;
  margin: 0;
}
</style>
