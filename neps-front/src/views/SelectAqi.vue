<template>
  <div class="wrapper">
    <HeaderBox title="选择AQI" :showLogout="true" />
    <div class="view-container">
      <div class="aqi-table-card">
        <table class="aqi-info-table">
          <tr v-for="item in aqiList" :key="item.aqiId">
            <td class="aqi-level">{{ item.aqiLevel }}</td>
            <td class="aqi-name" :style="{ color: item.aqiColor }">{{ item.aqiName }}</td>
            <td class="aqi-desc">{{ item.aqiDescription }}</td>
          </tr>
        </table>
      </div>

      <div class="view-card">
        <div class="select-label">请预估当前空气质量指数等级：</div>
        <div class="aqi-radio-group">
          <label v-for="item in aqiList" :key="item.aqiId" class="aqi-radio-item">
            <input type="radio" :value="item.aqiId" v-model="selectedAqiId" class="aqi-radio-input">
            <span class="aqi-radio-block" :style="{ backgroundColor: item.aqiColor }">{{ item.aqiLevel }}</span>
            <span class="aqi-radio-dot" :class="{ active: selectedAqiId === item.aqiId }"></span>
          </label>
        </div>
      </div>

      <div class="view-card">
        <div class="select-label">请填写反馈信息：</div>
        <textarea
            v-model="afContent"
            placeholder="请填写反馈信息"
            class="feedback-textarea"
        ></textarea>
      </div>

      <div class="btn" @click="submitFeedback">提交</div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage, removeSessionStorage, getCurDate, getCurTime } from '../common';
import HeaderBox from '../components/HeaderBox.vue';

const axios = inject('axios');
const router = useRouter();

const levelMap = { 1: '一', 2: '二', 3: '三', 4: '四', 5: '五', 6: '六' };

const aqiList = ref([]);
const selectedAqiId = ref(null);
const afContent = ref('');

onMounted(async () => {
  const res = await axios.get('/aqi/listAqiAll');
  const list = res.data.data || [];
  aqiList.value = list.map(item => ({
    ...item,
    aqiLevel: levelMap[item.aqiId] || ''
  }));
});

const submitFeedback = async () => {
  if (selectedAqiId.value === null) {
    alert('请选择AQI级别');
    return;
  }

  const supervisor = getSessionStorage('supervisor');
  const selectedProvince = getSessionStorage('selectedProvince');
  const selectedCity = getSessionStorage('selectedCity');
  const selectedAddress = getSessionStorage('selectedAddress');

  const data = {
    telId: supervisor.telId,
    province: selectedProvince ? selectedProvince.provinceName : (supervisor.province || ''),
    city: selectedCity ? selectedCity.cityName : '',
    afAddress: selectedAddress || '',
    afAqi: selectedAqiId.value,
    afDate: getCurDate(),
    afTime: getCurTime(),
    afImg: '',
    afContent: afContent.value
  };

  try {
    const res = await axios.post('/aqiFeedback/saveAqiFeedback', data);
    if (res.data.code === 200) {
      alert('反馈提交成功');
      router.push('/feedbackList');
    } else {
      alert(res.data.message);
    }
  } catch (error) {
    alert('提交失败，请重试');
  }
};
</script>

<style scoped>
.aqi-table-card {
  background: #FFF;
  border-radius: 2vw;
  margin-bottom: 3vw;
  overflow: hidden;
}

.aqi-info-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 3.2vw;
  border: 1px solid #e0e0e0;
}

.aqi-info-table tr {
  border-bottom: 1px solid #e0e0e0;
}

.aqi-info-table tr:last-child {
  border-bottom: none;
}

.aqi-info-table td {
  padding: 3vw 2.5vw;
  vertical-align: middle;
  border-right: 1px solid #e0e0e0;
}

.aqi-info-table td:last-child {
  border-right: none;
}

.aqi-level {
  width: 12vw;
  text-align: center;
  font-size: 4vw;
  font-weight: bold;
  color: #333;
}

.aqi-name {
  width: 18vw;
  font-weight: bold;
  font-size: 3.8vw;
}

.aqi-desc {
  color: #555;
  font-size: 13px;
  line-height: 1.2;
  font-weight: bolder;
}

.select-label {
  font-size: 4vw;
  font-weight: bold;
  color: #333;
  margin-bottom: 3vw;
}

.aqi-radio-group {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.aqi-radio-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5vw;
  cursor: pointer;
}

.aqi-radio-input {
  display: none;
}

.aqi-radio-block {
  width: 10vw;
  height: 6vw;
  border-radius: 1vw;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #000000;
  font-size: 15px;
  font-weight: bold;
}

.aqi-radio-dot {
  width: 4.5vw;
  height: 4.5vw;
  border-radius: 50%;
  border: 2px solid #ccc;
  background: #FFF;
  box-sizing: border-box;
  transition: all 0.2s;
  flex-shrink: 0;
}

.aqi-radio-dot.active {
  border-color: #4A90D9;
  background: radial-gradient(circle, #4A90D9 40%, #FFF 45%);
}

.feedback-textarea {
  width: 100%;
  min-height: 25vw;
  border: 1px solid #ddd;
  border-radius: 2vw;
  padding: 3vw;
  font-size: 3.5vw;
  color: #333;
  background: #fafafa;
  resize: vertical;
  box-sizing: border-box;
  outline: none;
}

.feedback-textarea::placeholder {
  color: #bbb;
}

.feedback-textarea:focus {
  border-color: #74bfd0;
}

.btn{
  height: 54px;
  width: 100%;
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}


/* ===== 响应式适配 ===== */
@media (min-width: 768px) {
  .aqi-info-table {
    font-size: 14px;
  }

  .aqi-level {
    width: 60px;
    font-size: 18px;
  }

  .aqi-name {
    width: 90px;
    font-size: 16px;
  }

  .aqi-desc {
    font-size: 13px;
    padding: 12px 10px;
  }

  .select-label {
    font-size: 16px;
    margin-bottom: 12px;
  }

  .aqi-radio-group {
    gap: 12px;
  }

  .aqi-radio-block {
    width: 44px;
    height: 30px;
    border-radius: 4px;
    font-size: 14px;
  }

  .aqi-radio-dot {
    width: 18px;
    height: 18px;
  }

  .feedback-textarea {
    min-height: 120px;
    font-size: 14px;
    padding: 12px;
  }
}
</style>
