<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>反馈列表</span>
          <div style="display: flex; gap: 10px;">
            <el-select v-model="statusFilter" placeholder="状态筛选" style="width: 120px;" @change="handleFilter">
              <el-option label="全部" value="" />
              <el-option label="待指派" value="0" />
              <el-option label="已指派" value="1" />
              <el-option label="已确认" value="2" />
            </el-select>
            <el-input v-model="keyword" placeholder="搜索" style="width: 200px;" @keyup.enter="loadData">
              <template #append>
                <el-button @click="loadData">搜索</el-button>
              </template>
            </el-input>
          </div>
        </div>
      </template>
      
      <!-- 状态统计 -->
      <div style="display: flex; gap: 20px; margin-bottom: 20px;">
        <el-tag :type="statusFilter === '' ? '' : 'info'" style="cursor: pointer;" @click="setStatusFilter('')">
          全部: {{ totalCount }}
        </el-tag>
        <el-tag type="warning" :effect="statusFilter === '0' ? 'dark' : 'plain'" style="cursor: pointer;" @click="setStatusFilter('0')">
          待指派: {{ pendingCount }}
        </el-tag>
        <el-tag type="primary" :effect="statusFilter === '1' ? 'dark' : 'plain'" style="cursor: pointer;" @click="setStatusFilter('1')">
          已指派: {{ assignedCount }}
        </el-tag>
        <el-tag type="success" :effect="statusFilter === '2' ? 'dark' : 'plain'" style="cursor: pointer;" @click="setStatusFilter('2')">
          已确认: {{ confirmedCount }}
        </el-tag>
      </div>
      
      <el-table :data="feedbackList" stripe style="width: 100%;">
        <el-table-column prop="afId" label="编号" width="80" />
        <el-table-column prop="province" label="省" width="100" />
        <el-table-column prop="city" label="市" width="100" />
        <el-table-column prop="afAddress" label="地址" show-overflow-tooltip />
        <el-table-column prop="afAqi" label="预估AQI" width="100">
          <template #default="scope">
            <el-tag :type="getAqiType(scope.row.afAqi)" size="small">{{ scope.row.afAqi }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="afDate" label="反馈日期" width="120" />
        <el-table-column label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStateType(scope.row.afState)">{{ getStateText(scope.row.afState) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="viewDetail(scope.row)">详情</el-button>
            <el-button v-if="scope.row.afState === 0" size="small" type="primary" @click="assign(scope.row)">指派</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 20px; justify-content: center;" 
        v-model:current-page="pageNum" v-model:page-size="pageSize" :total="total"
        layout="total, prev, pager, next" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const axios = inject('axios');
const router = useRouter();

const keyword = ref('');
const statusFilter = ref('');
const feedbackList = ref([]);
const allFeedbackList = ref([]);
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);

const totalCount = ref(0);
const pendingCount = ref(0);
const assignedCount = ref(0);
const confirmedCount = ref(0);

const loadData = async () => {
  const res = await axios.get('/aqiFeedback/listAqiFeedbackPage', {
    params: { keyword: keyword.value, pageNum: 1, pageSize: 1000 }
  });
  allFeedbackList.value = res.data.data.list;
  
  // 统计各状态数量
  totalCount.value = allFeedbackList.value.length;
  pendingCount.value = allFeedbackList.value.filter(item => item.afState === 0).length;
  assignedCount.value = allFeedbackList.value.filter(item => item.afState === 1).length;
  confirmedCount.value = allFeedbackList.value.filter(item => item.afState === 2).length;
  
  // 筛选
  filterData();
};

const filterData = () => {
  let filtered = allFeedbackList.value;
  
  if (statusFilter.value !== '') {
    const state = parseInt(statusFilter.value);
    filtered = filtered.filter(item => item.afState === state);
  }
  
  total.value = filtered.length;
  const start = (pageNum.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  feedbackList.value = filtered.slice(start, end);
};

const handleFilter = () => {
  pageNum.value = 1;
  filterData();
};

const setStatusFilter = (status) => {
  statusFilter.value = status;
  handleFilter();
};

onMounted(loadData);

const getStateText = (state) => {
  const map = { 0: '待指派', 1: '已指派', 2: '已确认' };
  return map[state] || '未知';
};

const getStateType = (state) => {
  const map = { 0: 'warning', 1: 'primary', 2: 'success' };
  return map[state] || 'info';
};

const getAqiType = (aqi) => {
  if (aqi <= 2) return 'success';
  if (aqi <= 3) return 'warning';
  return 'danger';
};

const viewDetail = (row) => {
  sessionStorage.setItem('selectedFeedback', JSON.stringify(row));
  router.push('/index/feedbackInfo');
};

const assign = (row) => {
  sessionStorage.setItem('selectedFeedback', JSON.stringify(row));
  router.push('/index/assignFeedback');
};
</script>
