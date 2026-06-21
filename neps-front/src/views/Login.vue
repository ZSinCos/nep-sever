<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <img src="../assets/logo.png" style="width: 50px;">
        <span class="view-text-lg">公众监督员登录</span>
        <i class="fa fa-bars"></i>
      </header>
      <div class="top-ban"></div>
    </div>
    <table class="table-form">
      <tr>
        <td>手机号码</td>
        <td><input type="text" v-model="telId" placeholder="请输入手机号码"></td>
      </tr>
      <tr>
        <td>密码</td>
        <td><input type="password" v-model="supervisorPassword" placeholder="请输入密码"></td>
      </tr>
    </table>
    <div class="view-container" style="margin-top: 0;">
      <div class="btn" @click="login">登 录</div>
      <div class="btn" style="background-color: #999;" @click="goRegister">注 册</div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { setSessionStorage } from '../common';

const axios = inject('axios');
const router = useRouter();

const telId = ref('');
const supervisorPassword = ref('');

const login = async () => {
  if (!telId.value || !supervisorPassword.value) {
    alert('请输入手机号码和密码');
    return;
  }
  try {
    const res = await axios.get('/supervisor/getSupervisorByIdByPass', {
      params: { telId: telId.value, supervisorPassword: supervisorPassword.value }
    });
    if (res.data.code === 200) {
      setSessionStorage('supervisor', res.data.data);
      router.push('/selectGrid');
    } else {
      alert(res.data.message);
    }
  } catch (error) {
    alert('登录失败，请重试');
  }
};

const goRegister = () => {
  router.push('/register');
};
</script>
