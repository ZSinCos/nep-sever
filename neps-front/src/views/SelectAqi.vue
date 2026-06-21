<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-arrow-left" @click="goBack"></i>
        <span class="view-text-lg">预估AQI</span>
        <i class="fa fa-sign-out" @click="logout"></i>
      </header>
      <div class="top-ban"></div>
    </div>
    <div class="view-container">
      <div class="view-card">
        <div class="view-card-title">选择AQI级别</div>
        <div v-for="item in aqiList" :key="item.aqiId"
             :style="{background: item.aqiColor, margin: '2vw 0', padding: '3vw', borderRadius: '2vw', cursor: 'pointer', opacity: selectedAqi === item ? 1 : 0.6}"
             @click="selectedAqi = item">
          <div style="font-weight: bold; color: #FFF;">{{ item.aqiName }} ({{ item.aqiMin }}-{{ item.aqiMax }})</div>
          <div style="color: #FFF; font-size: 3.5vw;">{{ item.aqiDescription }}</div>
        </div>
      </div>
      <div class="view-card">
        <div class="view-card-title">反馈信息</div>
        <table class="table-form" style="margin: 0;">
          <tr>
            <td>详细地址</td>
            <td><input type="text" v-model="feedback.afAddress" placeholder="请输入详细地址"></td>
          </tr>
          <tr>
            <td>反馈内容</td>
            <td><textarea v-model="feedback.afContent" placeholder="请输入反馈内容" style="width: 100%; height: 80px; max-height: 20vw;"></textarea></td>
          </tr>
        </table>
      </div>
      <div class="btn" @click="submitFeedback">提交反馈</div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage, removeSessionStorage, getCurDate, getCurTime } from '../common';

const axios = inject('axios');
const router = useRouter();

const aqiList = ref([]);
const selectedAqi = ref(null);
const feedback = ref({
  afAddress: '',
  afContent: ''
});

onMounted(async () => {
  const res = await axios.get('/aqi/listAqiAll');
  aqiList.value = res.data.data;
});

const submitFeedback = async () => {
  if (!selectedAqi.value) {
    alert('请选择AQI级别');
    return;
  }
  if (!feedback.value.afAddress) {
    alert('请输入详细地址');
    return;
  }
  
  const supervisor = getSessionStorage('supervisor');
  const city = getSessionStorage('selectedCity');
  
  const data = {
    telId: supervisor.telId,
    province: supervisor.province || '',
    city: city ? city.cityName : '',
    afAddress: feedback.value.afAddress,
    afAqi: selectedAqi.value.aqiId,
    afDate: getCurDate(),
    afTime: getCurTime(),
    afImg: '',
    afContent: feedback.value.afContent
  };
  
  try {
    const res = await axios.post('/aqiFeedback/saveAqiFeedback', data);
    if (res.data.code === 200) {
      alert('反馈提交成功');
      router.push('/feedbackList');
    } else {
      alert(res.data.message);
    }
  } catch (error) {
    alert('提交失败，请重试');
  }
};

const goBack = () => router.back();
const logout = () => {
  removeSessionStorage('supervisor');
  router.push('/login');
};
</script>
