<template>
  <div style="padding: 20px;">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header><span>待指派反馈</span></template>
          <div style="text-align: center; font-size: 36px; color: #E6A23C; padding: 20px;">{{ pendingCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header><span>已指派反馈</span></template>
          <div style="text-align: center; font-size: 36px; color: #409EFF; padding: 20px;">{{ assignedCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header><span>已确认反馈</span></template>
          <div style="text-align: center; font-size: 36px; color: #67C23A; padding: 20px;">{{ confirmedCount }}</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header><span>快捷操作</span></template>
          <el-space wrap>
            <el-button type="primary" @click="$router.push('/index/feedbackList')">反馈列表</el-button>
            <el-button type="success" @click="$router.push('/index/statisticsList')">确认列表</el-button>
            <el-button type="warning" @click="$router.push('/index/provinceItemTotalStatis')">省AQI统计</el-button>
            <el-button type="info" @click="$router.push('/index/otherTotalStatis')">其他统计</el-button>
          </el-space>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header><span>系统信息</span></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="系统名称">东软环保公众监督系统</el-descriptions-item>
            <el-descriptions-item label="版本">v1.0.0</el-descriptions-item>
            <el-descriptions-item label="当前时间">{{ currentTime }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, onUnmounted } from 'vue';

const axios = inject('axios');

const pendingCount = ref(0);
const assignedCount = ref(0);
const confirmedCount = ref(0);
const currentTime = ref('');

let timer = null;

const loadData = async () => {
  try {
    // 获取待指派数量
    const res1 = await axios.get('/aqiFeedback/listAqiFeedbackPage', {
      params: { keyword: '', pageNum: 1, pageSize: 1 }
    });
    
    // 获取各状态数量
    const res2 = await axios.get('/aqiFeedback/listAqiFeedbackPage', {
      params: { keyword: '', pageNum: 1, pageSize: 1000 }
    });
    
    if (res2.data.code === 200) {
      const list = res2.data.data.list || [];
      pendingCount.value = list.filter(item => item.afState === 0).length;
      assignedCount.value = list.filter(item => item.afState === 1).length;
      confirmedCount.value = list.filter(item => item.afState === 2).length;
    }
  } catch (error) {
    console.error('加载数据失败:', error);
  }
};

const updateTime = () => {
  const now = new Date();
  currentTime.value = now.toLocaleString('zh-CN');
};

onMounted(() => {
  loadData();
  updateTime();
  timer = setInterval(updateTime, 1000);
});

onUnmounted(() => {
  if (timer) {
    clearInterval(timer);
  }
});
</script>
