<template>
  <div>
    <el-card>
      <template #header>
        <span>指派网格员</span>
      </template>
      <el-descriptions :column="2" border style="margin-bottom: 20px;">
        <el-descriptions-item label="反馈编号">{{ feedback.afId }}</el-descriptions-item>
        <el-descriptions-item label="区域">{{ feedback.province }} {{ feedback.city }}</el-descriptions-item>
        <el-descriptions-item label="地址" :span="2">{{ feedback.afAddress }}</el-descriptions-item>
      </el-descriptions>
      <el-form :model="form" label-width="100px">
        <el-form-item label="选择网格员">
          <el-select v-model="form.gmId" placeholder="请选择网格员">
            <el-option v-for="item in gridMemberList" :key="item.gmId" :label="item.gmName + ' (' + item.gmTel + ')'" :value="item.gmId" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAssign">确认指派</el-button>
          <el-button @click="goBack">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage, getCurDate, getCurTime } from '../../common';

const axios = inject('axios');
const myElMessage = inject('myElMessage');
const router = useRouter();

const feedback = ref({});
const gridMemberList = ref([]);
const form = ref({ gmId: '' });

onMounted(async () => {
  feedback.value = getSessionStorage('selectedFeedback') || {};
  const res = await axios.get('/gridMember/listGridMemberByProvinceId', {
    params: { provinceId: '', cityId: '' }
  });
  gridMemberList.value = res.data.data || [];
});

const submitAssign = async () => {
  if (!form.value.gmId) {
    myElMessage('请选择网格员', 'warning');
    return;
  }
  const admins = getSessionStorage('admins');
  const data = {
    afId: feedback.value.afId,
    gmId: form.value.gmId,
    assignDate: getCurDate(),
    assignTime: getCurTime(),
    adminId: admins.adminId
  };
  const res = await axios.post('/aqiFeedback/updateAqiFeedbackAssign', data);
  if (res.data.code === 200) {
    myElMessage('指派成功', 'success');
    router.push('/index/feedbackList');
  } else {
    myElMessage(res.data.message, 'error');
  }
};

const goBack = () => router.back();
</script>
