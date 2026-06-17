<template>
  <div>
    <el-card>
      <template #header>
        <span>确认详情</span>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="统计编号">{{ statistics.staId }}</el-descriptions-item>
        <el-descriptions-item label="反馈编号">{{ statistics.afId }}</el-descriptions-item>
        <el-descriptions-item label="网格员">{{ statistics.gmId }}</el-descriptions-item>
        <el-descriptions-item label="确认AQI">{{ statistics.staAqi }}</el-descriptions-item>
        <el-descriptions-item label="PM2.5">{{ statistics.staPm25 }}</el-descriptions-item>
        <el-descriptions-item label="PM10">{{ statistics.staPm10 }}</el-descriptions-item>
        <el-descriptions-item label="SO2">{{ statistics.staSo2 }}</el-descriptions-item>
        <el-descriptions-item label="NO2">{{ statistics.staNo2 }}</el-descriptions-item>
        <el-descriptions-item label="CO">{{ statistics.staCo }}</el-descriptions-item>
        <el-descriptions-item label="O3">{{ statistics.staO3 }}</el-descriptions-item>
        <el-descriptions-item label="确认时间">{{ statistics.staDate }} {{ statistics.staTime }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ statistics.staRemark }}</el-descriptions-item>
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
const statistics = ref({});

onMounted(() => {
  statistics.value = getSessionStorage('selectedStatistics') || {};
});

const goBack = () => router.back();
</script>
