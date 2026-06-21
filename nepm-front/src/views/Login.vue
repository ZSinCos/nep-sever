<template>
  <div class="login-container">
    <div class="login-box">
      <h2>东软环保公众监督系统</h2>
      <h3>系统管理端</h3>
      <el-form :model="form" style="margin-top: 30px;">
        <el-form-item>
          <el-input v-model="form.adminId" placeholder="管理员编码" prefix-icon="User"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.adminPassword" type="password" placeholder="密码" prefix-icon="Lock"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%;" @click="login">登 录</el-button>
        </el-form-item>
      </el-form>
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

const form = ref({
  adminId: '',
  adminPassword: ''
});

const login = async () => {
  if (!form.value.adminId || !form.value.adminPassword) {
    myElMessage('请输入管理员编码和密码', 'warning');
    return;
  }
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
  }
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #70B0BC 0%, #CFE8E9 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}
.login-box {
  width: 400px;
  max-width: 100%;
  padding: 40px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
}
.login-box h2 {
  color: #333;
  margin-bottom: 10px;
}
.login-box h3 {
  color: #666;
  font-weight: normal;
  margin-bottom: 20px;
}
@media (max-width: 768px) {
  .login-box {
    padding: 24px 16px;
  }
  .login-box h2 {
    font-size: 18px;
  }
}
</style>
