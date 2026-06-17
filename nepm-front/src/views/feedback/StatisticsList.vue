<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>确认列表</span>
          <el-input v-model="keyword" placeholder="搜索" style="width: 200px;" @keyup.enter="loadData">
            <template #append>
              <el-button @click="loadData">搜索</el-button>
            </template>
          </el-input>
        </div>
      </template>
      <el-table :data="statisticsList" stripe style="width: 100%;">
        <el-table-column prop="staId" label="编号" width="80" />
        <el-table-column prop="afId" label="反馈编号" width="100" />
        <el-table-column prop="gmId" label="网格员" width="100" />
        <el-table-column prop="staAqi" label="确认AQI" width="100" />
        <el-table-column prop="staPm25" label="PM2.5" width="100" />
        <el-table-column prop="staPm10" label="PM10" width="100" />
        <el-table-column prop="staDate" label="确认日期" width="120" />
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button size="small" @click="viewDetail(scope.row)">详情</el-button>
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
const statisticsList = ref([]);
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);

const loadData = async () => {
  const res = await axios.get('/statistics/listStatisticsPage', {
    params: { keyword: keyword.value, pageNum: pageNum.value, pageSize: pageSize.value }
  });
  statisticsList.value = res.data.data.list;
  total.value = res.data.data.total;
};

onMounted(loadData);

const viewDetail = (row) => {
  sessionStorage.setItem('selectedStatistics', JSON.stringify(row));
  router.push('/index/statisticsInfo');
};
</script>
