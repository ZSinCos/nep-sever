<template>
  <div style="height: 100%;">
    <el-container style="height: 100%;">
      <el-header class="app-header">
        <div class="header-left">
          <div class="logo">
            <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" width="28" height="28">
              <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" fill="#fff"/>
            </svg>
            <span class="logo-text">东软环保公众监督系统</span>
          </div>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="32" class="user-avatar">
                {{ adminName.charAt(0) }}
              </el-avatar>
              <span class="user-name">{{ adminName }}</span>
              <el-icon><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>个人信息
                </el-dropdown-item>
                <el-dropdown-item command="password">
                  <el-icon><Lock /></el-icon>修改密码
                </el-dropdown-item>
                <el-dropdown-item divided command="logout">
                  <el-icon><SwitchButton /></el-icon>退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside :width="isCollapse ? '64px' : '220px'" class="app-aside">
          <div class="menu-toggle" @click="isCollapse = !isCollapse">
            <el-icon v-if="isCollapse"><Expand /></el-icon>
            <el-icon v-else><Fold /></el-icon>
          </div>
          <el-menu 
            :default-active="activeMenu" 
            :collapse="isCollapse"
            background-color="#001529" 
            text-color="rgba(255,255,255,0.65)" 
            active-text-color="#fff"
            router
            class="side-menu"
          >
            <el-menu-item index="/index">
              <el-icon><HomeFilled /></el-icon>
              <span>首页</span>
            </el-menu-item>
            
            <el-sub-menu index="feedback">
              <template #title>
                <el-icon><Document /></el-icon>
                <span>反馈管理</span>
              </template>
              <el-menu-item index="/index/feedbackList">
                <el-icon><List /></el-icon>
                <span>反馈列表</span>
              </el-menu-item>
              <el-menu-item index="/index/statisticsList">
                <el-icon><DataBoard /></el-icon>
                <span>确认列表</span>
              </el-menu-item>
            </el-sub-menu>
            
            <el-sub-menu index="statistics">
              <template #title>
                <el-icon><DataAnalysis /></el-icon>
                <span>统计分析</span>
              </template>
              <el-menu-item index="/index/provinceItemTotalStatis">
                <el-icon><MapLocation /></el-icon>
                <span>省AQI统计</span>
              </el-menu-item>
              <el-menu-item index="/index/aqiDistributeTotalStatis">
                <el-icon><PieChart /></el-icon>
                <span>AQI分布统计</span>
              </el-menu-item>
              <el-menu-item index="/index/aqiTrendTotalStatis">
                <el-icon><TrendCharts /></el-icon>
                <span>AQI趋势统计</span>
              </el-menu-item>
              <el-menu-item index="/index/otherTotalStatis">
                <el-icon><Odometer /></el-icon>
                <span>其他统计</span>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main class="app-main">
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
import { ElMessageBox, ElMessage } from 'element-plus';
import { 
  HomeFilled, Document, DataAnalysis, List, DataBoard,
  MapLocation, PieChart, TrendCharts, Odometer,
  ArrowDown, User, Lock, SwitchButton, Expand, Fold
} from '@element-plus/icons-vue';

const router = useRouter();
const route = useRoute();

const isCollapse = ref(false);

const adminName = computed(() => {
  const admins = getSessionStorage('admins');
  return admins ? admins.adminName : '管理员';
});

const activeMenu = computed(() => route.path);

const handleCommand = (command) => {
  if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      removeSessionStorage('admins');
      ElMessage.success('已退出登录');
      router.push('/login');
    }).catch(() => {});
  } else if (command === 'profile') {
    ElMessage.info('个人信息功能开发中...');
  } else if (command === 'password') {
    ElMessage.info('修改密码功能开发中...');
  }
};
</script>

<style scoped>
.app-header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  z-index: 100;
}

.header-left {
  display: flex;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo svg {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 8px;
  padding: 4px;
}

.logo-text {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  white-space: nowrap;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 8px;
  transition: background 0.3s;
}

.user-info:hover {
  background: #f5f5f5;
}

.user-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  font-weight: bold;
}

.user-name {
  font-size: 14px;
  color: #333;
}

.app-aside {
  background: #001529;
  transition: width 0.3s;
  overflow: hidden;
}

.menu-toggle {
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.65);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.menu-toggle:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.05);
}

.side-menu {
  border-right: none;
}

.side-menu:not(.el-menu--collapse) {
  width: 220px;
}

:deep(.el-menu-item.is-active) {
  background: #1890ff !important;
}

:deep(.el-menu-item:hover) {
  background: rgba(255, 255, 255, 0.05) !important;
}

:deep(.el-sub-menu__title:hover) {
  background: rgba(255, 255, 255, 0.05) !important;
}

.app-main {
  background: #f0f2f5;
  padding: 0;
  overflow: auto;
}
</style>
