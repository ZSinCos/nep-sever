<template>
  <div>
    <el-card>
      <template #header>
        <span>AQI等级分布统计</span>
      </template>
      <div ref="chartRef" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, nextTick } from 'vue';
import * as echarts from 'echarts';

const axios = inject('axios');
const chartRef = ref(null);

onMounted(async () => {
  const res = await axios.get('/statistics/listAqiDistributeTotalStatis');
  const data = res.data.data || [];
  
  await nextTick();
  const chart = echarts.init(chartRef.value);
  chart.setOption({
    title: { text: 'AQI等级分布统计' },
    tooltip: { trigger: 'item' },
    series: [{
      type: 'pie',
      radius: '60%',
      data: data.map(item => ({ name: item.aqiName, value: item.count })),
      emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } }
    }]
  });
  window.addEventListener('resize', () => chart.resize());
});
</script>
