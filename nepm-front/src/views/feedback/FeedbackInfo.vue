<template>
  <div>
    <el-card>
      <template #header>
        <span>反馈详情</span>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="反馈编号">{{ feedback.afId }}</el-descriptions-item>
        <el-descriptions-item label="监督员">{{ feedback.telId }}</el-descriptions-item>
        <el-descriptions-item label="省">{{ feedback.province }}</el-descriptions-item>
        <el-descriptions-item label="市">{{ feedback.city }}</el-descriptions-item>
        <el-descriptions-item label="地址" :span="2">{{ feedback.afAddress }}</el-descriptions-item>
        <el-descriptions-item label="预估AQI">{{ feedback.afAqi }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStateType(feedback.afState)">{{ getStateText(feedback.afState) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="反馈时间">{{ feedback.afDate }} {{ feedback.afTime }}</el-descriptions-item>
        <el-descriptions-item label="指派网格员">{{ feedback.gmId || '未指派' }}</el-descriptions-item>
        <el-descriptions-item label="反馈内容" :span="2">{{ feedback.afContent }}</el-descriptions-item>
      </el-descriptions>
      <el-button style="margin-top: 20px;" @click="goBack">返回</el-button>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage } from '../../common';

const router = useRouter();
const feedback = ref({});

onMounted(() => {
  feedback.value = getSessionStorage('selectedFeedback') || {};
});

const getStateText = (state) => {
  const map = { 0: '待指派', 1: '已指派', 2: '已确认' };
  return map[state] || '未知';
};

const getStateType = (state) => {
  const map = { 0: 'warning', 1: 'primary', 2: 'success' };
  return map[state] || 'info';
};

const goBack = () => router.back();
</script>
