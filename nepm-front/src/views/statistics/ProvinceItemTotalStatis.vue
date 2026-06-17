<template>
  <div>
    <el-card>
      <template #header>
        <span>省AQI超标统计</span>
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
  const res = await axios.get('/statistics/listProvinceItemTotalStatis');
  const data = res.data.data || [];
  
  await nextTick();
  const chart = echarts.init(chartRef.value);
  chart.setOption({
    title: { text: '各省AQI超标累计统计' },
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: data.map(item => item.provinceName), axisLabel: { rotate: 45 } },
    yAxis: { type: 'value' },
    series: [
      { name: '超标次数', type: 'bar', data: data.map(item => item.exceedCount), itemStyle: { color: '#FF7E00' } },
      { name: '总次数', type: 'bar', data: data.map(item => item.totalCount), itemStyle: { color: '#70B0BC' } }
    ]
  });
  window.addEventListener('resize', () => chart.resize());
});
</script>
