<template>
  <div>
    <el-card>
      <template #header>
        <span>AQI超标趋势统计</span>
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
  const res = await axios.get('/statistics/listAqiTrendTotalStatis');
  const data = res.data.data || [];
  
  await nextTick();
  const chart = echarts.init(chartRef.value);
  chart.setOption({
    title: { text: '近12个月AQI超标趋势' },
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: data.map(item => item.month) },
    yAxis: { type: 'value' },
    series: [{
      name: '超标次数',
      type: 'line',
      data: data.map(item => item.count),
      smooth: true,
      areaStyle: { opacity: 0.3 },
      itemStyle: { color: '#FF7E00' }
    }]
  });
  window.addEventListener('resize', () => chart.resize());
});
</script>
