<template>
  <div style="height: 100%;">
    <el-container style="height: 100%;">
      <el-header style="background-color: #409EFF; display: flex; align-items: center; justify-content: space-between;">
        <div style="display: flex; align-items: center; gap: 12px;">
          <el-button class="menu-toggle" type="primary" text @click="toggleSidebar" style="color: #fff; font-size: 20px; display: none;">
            <el-icon><Expand /></el-icon>
          </el-button>
          <span style="color: #fff; font-size: 20px; font-weight: bold;">东软环保公众监督系统</span>
        </div>
        <div>
          <span style="color: #fff; margin-right: 20px;">{{ adminName }}</span>
          <el-button type="info" size="small" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside :width="sidebarWidth" style="background-color: #545c64; transition: width 0.3s; overflow: hidden;">
          <el-menu :default-active="activeMenu" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" router :collapse="isCollapse">
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
        <div v-if="isCollapse && isMobile" class="sidebar-overlay" @click="toggleSidebar"></div>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { getSessionStorage, removeSessionStorage } from '../common';
import { HomeFilled, Document, DataAnalysis, Expand } from '@element-plus/icons-vue';

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

const isMobile = ref(false);
const isCollapse = ref(false);

const sidebarWidth = computed(() => {
  if (isMobile.value) {
    return isCollapse.value ? '200px' : '0px';
  }
  return isCollapse.value ? '64px' : '200px';
});

const checkMobile = () => {
  isMobile.value = window.innerWidth < 768;
  if (isMobile.value) {
    isCollapse.value = false;
  }
};

const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value;
};

onMounted(() => {
  checkMobile();
  window.addEventListener('resize', checkMobile);
});

onUnmounted(() => {
  window.removeEventListener('resize', checkMobile);
});
</script>

<style scoped>
.menu-toggle {
  display: none !important;
}
@media (max-width: 768px) {
  .menu-toggle {
    display: inline-flex !important;
  }
  .sidebar-overlay {
    position: fixed;
    top: 60px;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    z-index: 998;
  }
  .el-aside {
    position: fixed;
    top: 60px;
    left: 0;
    bottom: 0;
    z-index: 999;
  }
  .el-header span {
    font-size: 16px !important;
  }
}
</style>
