<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-arrow-left" @click="goBack"></i>
        <span style="font-size: 5vw; font-weight: bold;">反馈详情</span>
        <i class="fa fa-sign-out" @click="logout"></i>
      </header>
      <div class="top-ban"></div>
    </div>
    <div style="width: 86vw; margin: 0 auto; margin-top: 5vw;">
      <div style="background-color: #FFF; padding: 4vw; border-radius: 2vw; margin-bottom: 3vw;">
        <div style="font-weight: bold; margin-bottom: 3vw;">反馈信息</div>
        <table class="table-form" style="margin: 0;">
          <tr>
            <td>区域</td>
            <td>{{ feedback.province }} {{ feedback.city }}</td>
          </tr>
          <tr>
            <td>地址</td>
            <td>{{ feedback.afAddress }}</td>
          </tr>
          <tr>
            <td>预估AQI</td>
            <td>{{ feedback.afAqi }}</td>
          </tr>
          <tr>
            <td>反馈时间</td>
            <td>{{ feedback.afDate }} {{ feedback.afTime }}</td>
          </tr>
          <tr>
            <td>反馈内容</td>
            <td>{{ feedback.afContent }}</td>
          </tr>
        </table>
      </div>
      <div v-if="feedback.afState === 1" style="background-color: #FFF; padding: 4vw; border-radius: 2vw; margin-bottom: 3vw;">
        <div style="font-weight: bold; margin-bottom: 3vw;">确认AQI数据</div>
        <table class="table-form" style="margin: 0;">
          <tr>
            <td>确认AQI级别</td>
            <td>
              <select v-model="statistics.staAqi">
                <option v-for="item in aqiList" :key="item.aqiId" :value="item.aqiId">{{ item.aqiName }}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td>PM2.5</td>
            <td><input type="number" v-model="statistics.staPm25" placeholder="PM2.5"></td>
          </tr>
          <tr>
            <td>PM10</td>
            <td><input type="number" v-model="statistics.staPm10" placeholder="PM10"></td>
          </tr>
          <tr>
            <td>SO2</td>
            <td><input type="number" v-model="statistics.staSo2" placeholder="SO2"></td>
          </tr>
          <tr>
            <td>NO2</td>
            <td><input type="number" v-model="statistics.staNo2" placeholder="NO2"></td>
          </tr>
          <tr>
            <td>CO</td>
            <td><input type="number" v-model="statistics.staCo" placeholder="CO"></td>
          </tr>
          <tr>
            <td>O3</td>
            <td><input type="number" v-model="statistics.staO3" placeholder="O3"></td>
          </tr>
          <tr>
            <td>备注</td>
            <td><textarea v-model="statistics.staRemark" placeholder="备注" style="width: 100%; height: 15vw;"></textarea></td>
          </tr>
        </table>
        <div class="btn" @click="submitStatistics">提交确认数据</div>
      </div>
      <div v-if="feedback.afState === 2" style="background-color: #FFF; padding: 4vw; border-radius: 2vw; margin-bottom: 3vw;">
        <div style="font-weight: bold; margin-bottom: 3vw;">已确认数据</div>
        <div style="color: #00E400; text-align: center; padding: 5vw;">已完成确认</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage, removeSessionStorage, getCurDate, getCurTime } from '../common';

const axios = inject('axios');
const router = useRouter();

const feedback = ref({});
const aqiList = ref([]);
const statistics = ref({
  staAqi: '',
  staPm25: '',
  staPm10: '',
  staSo2: '',
  staNo2: '',
  staCo: '',
  staO3: '',
  staRemark: ''
});

onMounted(async () => {
  feedback.value = getSessionStorage('selectedFeedback') || {};
  const res = await axios.get('/aqi/listAqiAll');
  aqiList.value = res.data.data;
});

const submitStatistics = async () => {
  if (!statistics.value.staAqi) {
    alert('请选择AQI级别');
    return;
  }
  
  const gridMember = getSessionStorage('gridMember');
  
  const data = {
    afId: feedback.value.afId,
    gmId: gridMember.gmId,
    staAqi: statistics.value.staAqi,
    staPm25: statistics.value.staPm25 || null,
    staPm10: statistics.value.staPm10 || null,
    staSo2: statistics.value.staSo2 || null,
    staNo2: statistics.value.staNo2 || null,
    staCo: statistics.value.staCo || null,
    staO3: statistics.value.staO3 || null,
    staDate: getCurDate(),
    staTime: getCurTime(),
    staRemark: statistics.value.staRemark
  };
  
  try {
    const res = await axios.post('/statistics/saveStatistics', data);
    if (res.data.code === 200) {
      await axios.post('/aqiFeedback/updateAqiFeedbackState', {
        afId: feedback.value.afId,
        afState: 2
      });
      alert('确认成功');
      router.push('/feedbackList');
    } else {
      alert(res.data.message);
    }
  } catch (error) {
    alert('提交失败，请重试');
  }
};

const goBack = () => router.back();
const logout = () => {
  removeSessionStorage('gridMember');
  router.push('/login');
};
</script>
