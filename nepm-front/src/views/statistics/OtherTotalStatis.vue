<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header><span>AQI数据总量</span></template>
          <div style="text-align: center; font-size: 40px; color: #409EFF; padding: 20px;">{{ aqiCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header><span>AQI良好数据量</span></template>
          <div style="text-align: center; font-size: 40px; color: #67C23A; padding: 20px;">{{ aqiGoodCount }}</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header><span>AQI良好率</span></template>
          <div style="text-align: center; font-size: 40px; color: #E6A23C; padding: 20px;">{{ goodRate }}%</div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header><span>当前日期</span></template>
          <div style="text-align: center; font-size: 24px; color: #909399; padding: 20px;">{{ curDate }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, computed } from 'vue';
import { getCurDate } from '../../common';

const axios = inject('axios');

const aqiCount = ref(0);
const aqiGoodCount = ref(0);
const curDate = getCurDate();

const goodRate = computed(() => {
  if (aqiCount.value === 0) return 0;
  return ((aqiGoodCount.value / aqiCount.value) * 100).toFixed(1);
});

onMounted(async () => {
  const res1 = await axios.get('/statistics/getAqiCount');
  aqiCount.value = res1.data.data || 0;
  
  const res2 = await axios.get('/statistics/getAqiGoodCount');
  aqiGoodCount.value = res2.data.data || 0;
});
</script>
