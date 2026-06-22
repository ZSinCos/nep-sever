<template>
  <div class="wrapper">
    <HeaderBox title="历史反馈列表" :showLogout="true" />
    <HeroBanner
        bgImage="../assets/login-banner.png"
        slogan="../assets/register-slogan.png"
        sloganAlt="保护环境人人有责"
    />
    <div class="scroll-container">
      <div v-if="feedbackList.length === 0" class="view-empty">
        暂无反馈记录
      </div>
      <div v-for="item in feedbackList" :key="item.afId" class="view-card">
        <div class="view-flex-between view-mb-2">
          <span style="font-weight: bold; display: flex; align-items: center;">
            <span class="aqi-badge" :style="{backgroundColor: getAqiColor(item.afAqi)}">{{ getAqiLevel(item.afAqi) }}</span>
            {{ item.province }} {{ item.city }}
          </span>
          <span :style="{color: getStateColor(item.afState)}">{{ getStateText(item.afState) }}</span>
        </div>
        <div style="color: #666;" class="view-text-md view-mb-2">{{ item.afAddress }}</div>
        <div style="color: #999;" class="view-text-sm">
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
import HeroBanner from "@/components/HeroBanner.vue";
import HeaderBox from "@/components/HeaderBox.vue";

const axios = inject('axios');
const router = useRouter();

const feedbackList = ref([]);

const levelMap = { 1: '一', 2: '二', 3: '三', 4: '四', 5: '五', 6: '六' };
const colorMap = { 1: '#00E400', 2: '#CCCC00', 3: '#FF7E00', 4: '#FF0000', 5: '#99004C', 6: '#7E0023' };

onMounted(async () => {
  const supervisor = getSessionStorage('supervisor');
  if (supervisor) {
    const res = await axios.get('/aqiFeedback/listAqiFeedbackByTelId', {
      params: { telId: supervisor.telId }
    });
    feedbackList.value = res.data.data;
  }
});

const getAqiLevel = (aqiId) => levelMap[aqiId] || '';
const getAqiColor = (aqiId) => colorMap[aqiId] || '#999';

const getStateText = (state) => {
  const map = { 0: '待指派', 1: '已指派', 2: '已确认' };
  return map[state] || '未知';
};

const getStateColor = (state) => {
  const map = { 0: '#FF7E00', 1: '#4A90D9', 2: '#00E400' };
  return map[state] || '#999';
};

</script>

<style scoped>
.wrapper {
  display: flex;
  flex-direction: column;
}

.scroll-container {
  width: 86vw;
  margin: 0 auto;
  margin-top: 5vw;
  flex: 1;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
}

.aqi-badge {
  display: inline-block;
  width: 6vw;
  height: 4vw;
  border-radius: 1vw;
  color: #FFF;
  font-size: 2.5vw;
  font-weight: bold;
  text-align: center;
  line-height: 4vw;
  flex-shrink: 0;
  margin-right: 2vw;
}

@media (min-width: 768px) {
  .scroll-container {
    width: 400px;
    margin-top: 20px;
  }

  .aqi-badge {
    width: 28px;
    height: 20px;
    border-radius: 4px;
    font-size: 12px;
    line-height: 20px;
    margin-right: 8px;
  }
}
</style>
