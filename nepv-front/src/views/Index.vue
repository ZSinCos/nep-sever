<template>
  <div class="dashboard">
    <div class="header">
      <h1>东软环保公众监督系统 - 决策者端</h1>
      <div class="header-right">{{ curDate }}</div>
    </div>
    <div class="content">
      <div class="left">
        <div class="card">
          <div class="card-title">AQI数据总量</div>
          <div class="card-value" style="color: #409EFF;">{{ aqiCount }}</div>
        </div>
        <div class="card">
          <div class="card-title">AQI良好数据量</div>
          <div class="card-value" style="color: #67C23A;">{{ aqiGoodCount }}</div>
        </div>
        <div class="card">
          <div class="card-title">AQI良好率</div>
          <div class="card-value" style="color: #E6A23C;">{{ goodRate }}%</div>
        </div>
        <div class="chart-card">
          <div class="card-title">AQI等级分布</div>
          <div ref="pieChart" style="width: 100%; height: 250px;"></div>
        </div>
      </div>
      <div class="center">
        <div class="chart-card" style="height: 100%;">
          <div class="card-title">各省AQI超标累计统计</div>
          <div ref="barChart" style="width: 100%; height: calc(100% - 40px);"></div>
        </div>
      </div>
      <div class="right">
        <div class="chart-card">
          <div class="card-title">近12个月AQI超标趋势</div>
          <div ref="lineChart" style="width: 100%; height: 250px;"></div>
        </div>
        <div class="chart-card">
          <div class="card-title">各省超标排名</div>
          <div ref="rankChart" style="width: 100%; height: 250px;"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick, computed } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';

axios.defaults.baseURL = 'http://localhost:8080/nepm/';

const curDate = new Date().toLocaleDateString();
const aqiCount = ref(0);
const aqiGoodCount = ref(0);

const goodRate = computed(() => {
  if (aqiCount.value === 0) return 0;
  return ((aqiGoodCount.value / aqiCount.value) * 100).toFixed(1);
});

const pieChart = ref(null);
const barChart = ref(null);
const lineChart = ref(null);
const rankChart = ref(null);

onMounted(async () => {
  try {
    const res1 = await axios.get('/statistics/getAqiCount');
    aqiCount.value = res1.data.data || 0;
    
    const res2 = await axios.get('/statistics/getAqiGoodCount');
    aqiGoodCount.value = res2.data.data || 0;
    
    await nextTick();
    loadPieChart();
    loadBarChart();
    loadLineChart();
    loadRankChart();
  } catch (error) {
    console.error('加载数据失败:', error);
  }
});

const loadPieChart = async () => {
  const res = await axios.get('/statistics/listAqiDistributeTotalStatis');
  const data = res.data.data || [];
  const chart = echarts.init(pieChart.value);
  chart.setOption({
    tooltip: { trigger: 'item' },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      data: data.map(item => ({ name: item.aqiName, value: item.count })),
      label: { color: '#fff' }
    }]
  });
};

const loadBarChart = async () => {
  const res = await axios.get('/statistics/listProvinceItemTotalStatis');
  const data = res.data.data || [];
  const chart = echarts.init(barChart.value);
  chart.setOption({
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '10%', containLabel: true },
    xAxis: { 
      type: 'category', 
      data: data.map(item => item.provinceName), 
      axisLabel: { color: '#fff', rotate: 45, interval: 0 } 
    },
    yAxis: { type: 'value', axisLabel: { color: '#fff' } },
    series: [
      { name: '超标次数', type: 'bar', data: data.map(item => item.exceedCount), itemStyle: { color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{ offset: 0, color: '#FF7E00' }, { offset: 1, color: '#FF0000' }]) } }
    ]
  });
  window.addEventListener('resize', () => chart.resize());
};

const loadLineChart = async () => {
  const res = await axios.get('/statistics/listAqiTrendTotalStatis');
  const data = res.data.data || [];
  const chart = echarts.init(lineChart.value);
  chart.setOption({
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: data.map(item => item.month), axisLabel: { color: '#fff' } },
    yAxis: { type: 'value', axisLabel: { color: '#fff' } },
    series: [{
      name: '超标次数',
      type: 'line',
      data: data.map(item => item.count),
      smooth: true,
      areaStyle: { opacity: 0.3, color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{ offset: 0, color: '#FF7E00' }, { offset: 1, color: 'rgba(255,126,0,0)' }]) },
      lineStyle: { color: '#FF7E00' },
      itemStyle: { color: '#FF7E00' }
    }]
  });
  window.addEventListener('resize', () => chart.resize());
};

const loadRankChart = async () => {
  const res = await axios.get('/statistics/listProvinceItemTotalStatis');
  const data = (res.data.data || []).slice(0, 10);
  const chart = echarts.init(rankChart.value);
  chart.setOption({
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'value', axisLabel: { color: '#fff' } },
    yAxis: { 
      type: 'category', 
      data: data.map(item => item.provinceName).reverse(), 
      axisLabel: { color: '#fff' } 
    },
    series: [{
      name: '超标次数',
      type: 'bar',
      data: data.map(item => item.exceedCount).reverse(),
      itemStyle: { color: '#67C23A' }
    }]
  });
  window.addEventListener('resize', () => chart.resize());
};
</script>

<style scoped>
.dashboard {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  color: #fff;
  display: flex;
  flex-direction: column;
}
.header {
  height: 60px;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}
.header h1 {
  font-size: 24px;
  background: linear-gradient(90deg, #409EFF, #67C23A);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.header-right {
  color: #999;
}
.content {
  flex: 1;
  display: flex;
  padding: 10px;
  gap: 10px;
}
.left, .right {
  width: 25%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.center {
  flex: 1;
}
.card {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 15px;
  text-align: center;
}
.card-title {
  font-size: 14px;
  color: #999;
  margin-bottom: 10px;
}
.card-value {
  font-size: 36px;
  font-weight: bold;
}
.chart-card {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 15px;
  flex: 1;
}
</style>
