<template>
  <div style="padding: 20px; background: #f5f7fa; min-height: calc(100vh - 60px);">
    <!-- 欢迎信息 -->
    <div class="welcome-banner">
      <div class="welcome-text">
        <h2>欢迎回来，{{ adminName }}</h2>
        <p>今天是 {{ today }}，祝你工作愉快！</p>
      </div>
      <div class="welcome-stats">
        <div class="stat-item">
          <span class="stat-value">{{ pendingCount }}</span>
          <span class="stat-label">待处理</span>
        </div>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <div class="stat-card stat-card-warning">
          <div class="stat-card-icon">
            <el-icon size="40"><Warning /></el-icon>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value">{{ pendingCount }}</span>
            <span class="stat-card-label">待指派反馈</span>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-card-primary">
          <div class="stat-card-icon">
            <el-icon size="40"><Promotion /></el-icon>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value">{{ assignedCount }}</span>
            <span class="stat-card-label">已指派反馈</span>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-card-success">
          <div class="stat-card-icon">
            <el-icon size="40"><CircleCheck /></el-icon>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value">{{ confirmedCount }}</span>
            <span class="stat-card-label">已确认反馈</span>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-card-info">
          <div class="stat-card-icon">
            <el-icon size="40"><DataAnalysis /></el-icon>
          </div>
          <div class="stat-card-info">
            <span class="stat-card-value">{{ totalCount }}</span>
            <span class="stat-card-label">总反馈数</span>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 图表和快捷操作 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="16">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>近7天反馈趋势</span>
              <el-radio-group v-model="chartType" size="small">
                <el-radio-button label="line">折线图</el-radio-button>
                <el-radio-button label="bar">柱状图</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <div ref="trendChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="chart-card">
          <template #header><span>反馈状态分布</span></template>
          <div ref="pieChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷操作和最近反馈 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header><span>快捷操作</span></template>
          <div class="quick-actions">
            <el-button type="primary" @click="$router.push('/index/feedbackList')" class="action-btn">
              <el-icon><Document /></el-icon>
              <span>反馈列表</span>
            </el-button>
            <el-button type="success" @click="$router.push('/index/statisticsList')" class="action-btn">
              <el-icon><DataBoard /></el-icon>
              <span>确认列表</span>
            </el-button>
            <el-button type="warning" @click="$router.push('/index/provinceItemTotalStatis')" class="action-btn">
              <el-icon><TrendCharts /></el-icon>
              <span>省AQI统计</span>
            </el-button>
            <el-button type="info" @click="$router.push('/index/aqiDistributeTotalStatis')" class="action-btn">
              <el-icon><PieChart /></el-icon>
              <span>AQI分布</span>
            </el-button>
            <el-button type="danger" @click="$router.push('/index/aqiTrendTotalStatis')" class="action-btn">
              <el-icon><DataLine /></el-icon>
              <span>AQI趋势</span>
            </el-button>
            <el-button @click="$router.push('/index/otherTotalStatis')" class="action-btn">
              <el-icon><Odometer /></el-icon>
              <span>其他统计</span>
            </el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最近反馈</span>
              <el-button type="primary" link @click="$router.push('/index/feedbackList')">查看全部</el-button>
            </div>
          </template>
          <el-table :data="recentFeedback" stripe style="width: 100%;" max-height="300">
            <el-table-column prop="afId" label="编号" width="80" />
            <el-table-column prop="province" label="省" width="100" />
            <el-table-column prop="city" label="市" width="100" />
            <el-table-column prop="afAddress" label="地址" show-overflow-tooltip />
            <el-table-column label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getStateType(scope.row.afState)" size="small">
                  {{ getStateText(scope.row.afState) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template #default="scope">
                <el-button type="primary" link size="small" @click="viewFeedback(scope.row)">详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, onUnmounted, computed, watch, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage } from '../common';
import * as echarts from 'echarts';
import { 
  Warning, Promotion, CircleCheck, DataAnalysis,
  Document, DataBoard, TrendCharts, PieChart, 
  DataLine, Odometer
} from '@element-plus/icons-vue';

const axios = inject('axios');
const router = useRouter();

const pendingCount = ref(0);
const assignedCount = ref(0);
const confirmedCount = ref(0);
const totalCount = ref(0);
const recentFeedback = ref([]);
const chartType = ref('line');

const trendChart = ref(null);
const pieChart = ref(null);
let trendChartInstance = null;
let pieChartInstance = null;

const adminName = computed(() => {
  const admins = getSessionStorage('admins');
  return admins ? admins.adminName : '管理员';
});

const today = computed(() => {
  const date = new Date();
  const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
  return date.toLocaleDateString('zh-CN', options);
});

const getStateText = (state) => {
  const map = { 0: '待指派', 1: '已指派', 2: '已确认' };
  return map[state] || '未知';
};

const getStateType = (state) => {
  const map = { 0: 'warning', 1: 'primary', 2: 'success' };
  return map[state] || 'info';
};

const loadData = async () => {
  try {
    const res = await axios.get('/aqiFeedback/listAqiFeedbackPage', {
      params: { keyword: '', pageNum: 1, pageSize: 1000 }
    });
    
    if (res.data.code === 200) {
      const list = res.data.data.list || [];
      pendingCount.value = list.filter(item => item.afState === 0).length;
      assignedCount.value = list.filter(item => item.afState === 1).length;
      confirmedCount.value = list.filter(item => item.afState === 2).length;
      totalCount.value = list.length;
      recentFeedback.value = list.slice(0, 5);
    }
  } catch (error) {
    console.error('加载数据失败:', error);
  }
};

const initTrendChart = () => {
  if (!trendChart.value) return;
  
  trendChartInstance = echarts.init(trendChart.value);
  
  // 生成模拟数据
  const dates = [];
  const values = [];
  for (let i = 6; i >= 0; i--) {
    const date = new Date();
    date.setDate(date.getDate() - i);
    dates.push(date.toLocaleDateString('zh-CN', { month: 'short', day: 'numeric' }));
    values.push(Math.floor(Math.random() * 20) + 5);
  }
  
  const option = {
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: dates },
    yAxis: { type: 'value', name: '反馈数量' },
    series: [{
      data: values,
      type: chartType.value,
      smooth: true,
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
          { offset: 1, color: 'rgba(64, 158, 255, 0.05)' }
        ])
      },
      itemStyle: { color: '#409EFF' }
    }]
  };
  
  trendChartInstance.setOption(option);
};

const initPieChart = () => {
  if (!pieChart.value) return;
  
  pieChartInstance = echarts.init(pieChart.value);
  
  const option = {
    tooltip: { trigger: 'item' },
    legend: { bottom: '5%', left: 'center' },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 },
      label: { show: false },
      emphasis: {
        label: { show: true, fontSize: '14', fontWeight: 'bold' }
      },
      data: [
        { value: pendingCount.value, name: '待指派', itemStyle: { color: '#E6A23C' } },
        { value: assignedCount.value, name: '已指派', itemStyle: { color: '#409EFF' } },
        { value: confirmedCount.value, name: '已确认', itemStyle: { color: '#67C23A' } }
      ]
    }]
  };
  
  pieChartInstance.setOption(option);
};

const viewFeedback = (row) => {
  sessionStorage.setItem('selectedFeedback', JSON.stringify(row));
  router.push('/index/feedbackInfo');
};

watch(chartType, () => {
  if (trendChartInstance) {
    const option = trendChartInstance.getOption();
    option.series[0].type = chartType.value;
    trendChartInstance.setOption(option);
  }
});

onMounted(async () => {
  await loadData();
  await nextTick();
  initTrendChart();
  initPieChart();
});

onUnmounted(() => {
  if (trendChartInstance) trendChartInstance.dispose();
  if (pieChartInstance) pieChartInstance.dispose();
});
</script>

<style scoped>
.welcome-banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  padding: 30px;
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #fff;
}

.welcome-text h2 {
  margin: 0 0 8px 0;
  font-size: 24px;
  font-weight: 600;
}

.welcome-text p {
  margin: 0;
  opacity: 0.9;
  font-size: 14px;
}

.welcome-stats {
  display: flex;
  gap: 30px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 36px;
  font-weight: bold;
}

.stat-label {
  font-size: 12px;
  opacity: 0.8;
}

.stat-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s, box-shadow 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.stat-card-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-card-warning .stat-card-icon { background: linear-gradient(135deg, #f6d365 0%, #fda085 100%); }
.stat-card-primary .stat-card-icon { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.stat-card-success .stat-card-icon { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); }
.stat-card-info .stat-card-icon { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }

.stat-card-info {
  flex: 1;
}

.stat-card-value {
  display: block;
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stat-card-label {
  font-size: 14px;
  color: #999;
}

.chart-card {
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.quick-actions {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.action-btn {
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

:deep(.el-card) {
  border-radius: 12px;
  border: none;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

:deep(.el-card__header) {
  border-bottom: 1px solid #f0f0f0;
  padding: 15px 20px;
}
</style>
