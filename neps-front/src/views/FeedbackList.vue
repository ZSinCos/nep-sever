<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-arrow-left" @click="goBack"></i>
        <span style="font-size: 5vw; font-weight: bold;">历史反馈列表</span>
        <i class="fa fa-sign-out" @click="logout"></i>
      </header>
      <div class="top-ban"></div>
    </div>
    <div style="width: 86vw; margin: 0 auto; margin-top: 5vw;">
      <div v-if="feedbackList.length === 0" style="text-align: center; color: #999; padding: 10vw;">
        暂无反馈记录
      </div>
      <div v-for="item in feedbackList" :key="item.afId" 
           style="background-color: #FFF; padding: 4vw; border-radius: 2vw; margin-bottom: 3vw;">
        <div style="display: flex; justify-content: space-between; margin-bottom: 2vw;">
          <span style="font-weight: bold;">{{ item.province }} {{ item.city }}</span>
          <span :style="{color: getStateColor(item.afState)}">{{ getStateText(item.afState) }}</span>
        </div>
        <div style="color: #666; font-size: 3.5vw; margin-bottom: 2vw;">{{ item.afAddress }}</div>
        <div style="color: #999; font-size: 3vw;">
          {{ item.afDate }} {{ item.afTime }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage, removeSessionStorage } from '../common';

const axios = inject('axios');
const router = useRouter();

const feedbackList = ref([]);

onMounted(async () => {
  const supervisor = getSessionStorage('supervisor');
  if (supervisor) {
    const res = await axios.get('/aqiFeedback/listAqiFeedbackByTelId', {
      params: { telId: supervisor.telId }
    });
    feedbackList.value = res.data.data;
  }
});

const getStateText = (state) => {
  const map = { 0: '待指派', 1: '已指派', 2: '已确认' };
  return map[state] || '未知';
};

const getStateColor = (state) => {
  const map = { 0: '#FF7E00', 1: '#4A90D9', 2: '#00E400' };
  return map[state] || '#999';
};

const goBack = () => router.back();
const logout = () => {
  removeSessionStorage('supervisor');
  router.push('/login');
};
</script>
