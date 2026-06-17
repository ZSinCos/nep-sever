<template>
  <div style="height: 100%;">
    <el-container style="height: 100%;">
      <el-header style="background-color: #409EFF; display: flex; align-items: center; justify-content: space-between;">
        <span style="color: #fff; font-size: 20px; font-weight: bold;">东软环保公众监督系统</span>
        <div>
          <span style="color: #fff; margin-right: 20px;">{{ adminName }}</span>
          <el-button type="info" size="small" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px" style="background-color: #545c64;">
          <el-menu :default-active="activeMenu" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" router>
            <el-menu-item index="/index">
              <el-icon><HomeFilled /></el-icon>
              <span>首页</span>
            </el-menu-item>
            <el-sub-menu index="feedback">
              <template #title>
                <el-icon><Document /></el-icon>
                <span>反馈管理</span>
              </template>
              <el-menu-item index="/index/feedbackList">反馈列表</el-menu-item>
              <el-menu-item index="/index/statisticsList">确认列表</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="statistics">
              <template #title>
                <el-icon><DataAnalysis /></el-icon>
                <span>统计分析</span>
              </template>
              <el-menu-item index="/index/provinceItemTotalStatis">省AQI统计</el-menu-item>
              <el-menu-item index="/index/aqiDistributeTotalStatis">AQI分布统计</el-menu-item>
              <el-menu-item index="/index/aqiTrendTotalStatis">AQI趋势统计</el-menu-item>
              <el-menu-item index="/index/otherTotalStatis">其他统计</el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { getSessionStorage, removeSessionStorage } from '../common';
import { HomeFilled, Document, DataAnalysis } from '@element-plus/icons-vue';

const router = useRouter();
const route = useRoute();

const adminName = computed(() => {
  const admins = getSessionStorage('admins');
  return admins ? admins.adminName : '管理员';
});

const activeMenu = computed(() => route.path);

const logout = () => {
  removeSessionStorage('admins');
  router.push('/login');
};
</script>
