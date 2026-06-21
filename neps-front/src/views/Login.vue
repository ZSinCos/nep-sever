<template>
  <div class="auth-page login-page">
    <div class="auth-top login-top">
      <div class="auth-hero login-hero"></div>

      <div class="login-title">
        <h1>东软环保公众监督平台</h1>
        <div class="login-subtitle">
          <span class="line"></span>
          <span class="text">公众监督员端</span>
          <span class="line"></span>
        </div>
      </div>
    </div>

    <div class="auth-bottom login-bottom">
      <div class="auth-card login-card">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="84px" class="login-form">
          <el-form-item label="手机号码" prop="telId">
            <el-input v-model="form.telId" placeholder="请输入手机号码" />
          </el-form-item>

          <el-form-item label="密码" prop="supervisorPassword">
            <el-input
                v-model="form.supervisorPassword"
                type="password"
                placeholder="请输入密码"
                show-password
            />
          </el-form-item>
        </el-form>

        <button class="auth-btn register-btn" @click="goRegister">注册</button>
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
const formRef = ref(null);

const form = ref({
  telId: '',
  supervisorPassword: ''
});

const rules = {
  telId: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号码格式不正确', trigger: 'blur' }
  ],
  supervisorPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
};

const login = async () => {
  const valid = await formRef.value.validate().catch(() => false);
  if (!valid) return;

  try {
    const res = await axios.get('/supervisor/getSupervisorByIdByPass', {
      params: {
        telId: form.value.telId,
        supervisorPassword: form.value.supervisorPassword
      }
    });

    if (res.data.code === 200) {
      setSessionStorage('supervisor', res.data.data);
      router.push('/selectGrid');
    } else {
      alert(res.data.message || '登录失败');
    }
  } catch (error) {
    console.error(error);
    alert('登录失败，请重试');
  }
};

const goRegister = () => {
  router.push('/register');
};
</script>

<style scoped>
.login-hero {
  background-image: url('../assets/login-banner.png');
  height: clamp(180px, 32vh, 280px);
}

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

.login-card {
  min-height: 300px;
  margin-top: 20px;
}

.login-form {
  padding: 0 20px;
}

.auth-btn {
  margin-top: 15px;
  font-weight: bold;
}

@media (min-width: 768px) {
  .login-hero {
    height: 300px;
  }

  .login-title h1 {
    font-size: 34px;
  }

  .login-card {
    min-height: 360px;
  }
}
</style>
