<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <img src="../assets/logo.png" style="width: 50px;">
        <span style="font-size: 5vw; font-weight: bold;">网格员登录</span>
        <i class="fa fa-bars"></i>
      </header>
      <div class="top-ban"></div>
    </div>
    <table class="table-form">
      <tr>
        <td>网格员编码</td>
        <td><input type="text" v-model="gmId" placeholder="请输入网格员编码"></td>
      </tr>
      <tr>
        <td>密码</td>
        <td><input type="password" v-model="gmPassword" placeholder="请输入密码"></td>
      </tr>
    </table>
    <div style="width: 86vw; margin: 0 auto;">
      <div class="btn" @click="login">登 录</div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { setSessionStorage } from '../common';

const axios = inject('axios');
const router = useRouter();

const gmId = ref('');
const gmPassword = ref('');

const login = async () => {
  if (!gmId.value || !gmPassword.value) {
    alert('请输入网格员编码和密码');
    return;
  }
  try {
    const res = await axios.get('/gridMember/getGridMemberByCodeByPass', {
      params: { gmId: gmId.value, gmPassword: gmPassword.value }
    });
    if (res.data.code === 200) {
      setSessionStorage('gridMember', res.data.data);
      router.push('/feedbackList');
    } else {
      alert(res.data.message);
    }
  } catch (error) {
    alert('登录失败，请重试');
  }
};
</script>
