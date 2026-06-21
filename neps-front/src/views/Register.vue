<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-arrow-left" @click="goBack"></i>
        <span style="font-size: 5vw; font-weight: bold;">公众监督员注册</span>
        <i class="fa fa-bars"></i>
      </header>
      <div class="top-ban"></div>
    </div>
    <table class="table-form">
      <tr>
        <td>手机号码</td>
        <td><input type="text" v-model="supervisor.telId" placeholder="请输入手机号码"></td>
      </tr>
      <tr>
        <td>姓名</td>
        <td><input type="text" v-model="supervisor.supervisorName" placeholder="请输入姓名"></td>
      </tr>
      <tr>
        <td>密码</td>
        <td><input type="password" v-model="supervisor.supervisorPassword" placeholder="请输入密码"></td>
      </tr>
      <tr>
        <td>确认密码</td>
        <td><input type="password" v-model="confirmPassword" placeholder="请再次输入密码"></td>
      </tr>
      <tr>
        <td>省</td>
        <td>
          <select v-model="supervisor.province" @change="provinceChange">
            <option value="">请选择省</option>
            <option v-for="item in provinceList" :key="item.provinceId" :value="item.provinceName">{{ item.provinceName }}</option>
          </select>
        </td>
      </tr>
      <tr>
        <td>市</td>
        <td>
          <select v-model="supervisor.city">
            <option value="">请选择市</option>
            <option v-for="item in cityList" :key="item.cityId" :value="item.cityName">{{ item.cityName }}</option>
          </select>
        </td>
      </tr>
    </table>
    <div style="width: 86vw; margin: 0 auto;">
      <div class="btn" @click="register">注 册</div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getCurDate } from '../common';

const axios = inject('axios');
const router = useRouter();

const supervisor = ref({
  telId: '',
  supervisorName: '',
  supervisorPassword: '',
  province: '',
  city: '',
  registerDate: ''
});
const confirmPassword = ref('');
const provinceList = ref([]);
const cityList = ref([]);
const provinceIdMap = {};

onMounted(async () => {
  const res = await axios.get('/gridProvince/listGridProvinceAll');
  provinceList.value = res.data.data;
  provinceList.value.forEach(item => {
    provinceIdMap[item.provinceName] = item.provinceId;
  });
});

const provinceChange = async () => {
  supervisor.value.city = '';
  const provinceId = provinceIdMap[supervisor.value.province];
  if (provinceId) {
    const res = await axios.get('/gridCity/listGridCityByProvinceId', {
      params: { provinceId }
    });
    cityList.value = res.data.data;
  }
};

const register = async () => {
  if (!supervisor.value.telId || !supervisor.value.supervisorName || !supervisor.value.supervisorPassword) {
    alert('请填写完整信息');
    return;
  }
  if (supervisor.value.supervisorPassword !== confirmPassword.value) {
    alert('两次密码不一致');
    return;
  }
  if (!supervisor.value.province || !supervisor.value.city) {
    alert('请选择省和市');
    return;
  }
  // 验证手机号是否已注册
  try {
    const checkRes = await axios.get('/supervisor/getSupervisorById/' + supervisor.value.telId);
    if (checkRes.data.code === 200 && checkRes.data.data) {
      alert('该手机号码已被注册，请直接登录');
      return;
    }
  } catch (e) {
    // 查不到表示未注册，继续注册流程
  }
  supervisor.value.registerDate = getCurDate();
  try {
    const res = await axios.post('/supervisor/saveSupervisor', supervisor.value);
    if (res.data.code === 200) {
      alert('注册成功');
      router.push('/login');
    } else {
      alert(res.data.message);
    }
  } catch (error) {
    alert('注册失败，请重试');
  }
};

const goBack = () => {
  router.back();
};
</script>
