<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>反馈列表</span>
          <el-input v-model="keyword" placeholder="搜索" style="width: 200px;" @keyup.enter="loadData">
            <template #append>
              <el-button @click="loadData">搜索</el-button>
            </template>
          </el-input>
        </div>
      </template>
      <el-table :data="feedbackList" stripe style="width: 100%;">
        <el-table-column prop="afId" label="编号" width="80" />
        <el-table-column prop="province" label="省" width="100" />
        <el-table-column prop="city" label="市" width="100" />
        <el-table-column prop="afAddress" label="地址" />
        <el-table-column prop="afAqi" label="预估AQI" width="100" />
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
const feedbackList = ref([]);
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);

const loadData = async () => {
  const res = await axios.get('/aqiFeedback/listAqiFeedbackPage', {
    params: { keyword: keyword.value, pageNum: pageNum.value, pageSize: pageSize.value }
  });
  feedbackList.value = res.data.data.list;
  total.value = res.data.data.total;
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

const viewDetail = (row) => {
  sessionStorage.setItem('selectedFeedback', JSON.stringify(row));
  router.push('/index/feedbackInfo');
};

const assign = (row) => {
  sessionStorage.setItem('selectedFeedback', JSON.stringify(row));
  router.push('/index/assignFeedback');
};
</script>
