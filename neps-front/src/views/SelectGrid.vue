<template>
  <div class="wrapper">
    <div class="header-box">
      <header>
        <i class="fa fa-arrow-left" @click="goBack"></i>
        <span class="view-text-lg">网格分配</span>
        <i class="fa fa-sign-out" @click="logout"></i>
      </header>
      <div class="top-ban"></div>
    </div>
    <div class="view-container">
      <div class="view-card">
        <div class="view-card-title">选择监测区域</div>
        <table class="table-form" style="margin: 0;">
          <tr>
            <td>省</td>
            <td>
              <select v-model="selectedProvince" @change="provinceChange">
                <option value="">请选择省</option>
                <option v-for="item in provinceList" :key="item.provinceId" :value="item">{{ item.provinceName }}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td>市</td>
            <td>
              <select v-model="selectedCity" @change="cityChange">
                <option value="">请选择市</option>
                <option v-for="item in cityList" :key="item.cityId" :value="item">{{ item.cityName }}</option>
              </select>
            </td>
          </tr>
        </table>
      </div>
      <div class="view-card">
        <div class="view-card-title">可选网格员</div>
        <div v-if="gridMemberList.length === 0" class="view-empty">
          暂无可选网格员
        </div>
        <div v-for="item in gridMemberList" :key="item.gmId" 
             class="view-flex-between" style="padding: 3vw; border-bottom: 1px solid #eee;">
          <div>
            <div style="font-weight: bold;">{{ item.gmName }}</div>
            <div style="color: #999; font-size: 3.5vw;">{{ item.gmTel }}</div>
          </div>
          <div class="btn" style="width: 80px; height: 32px; line-height: 32px; font-size: 14px; margin: 0;" 
               @click="selectGridMember(item)">选择</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { getSessionStorage, removeSessionStorage } from '../common';

const axios = inject('axios');
const router = useRouter();

const selectedProvince = ref('');
const selectedCity = ref('');
const provinceList = ref([]);
const cityList = ref([]);
const gridMemberList = ref([]);

const loadProvinces = async () => {
  const res = await axios.get('/gridProvince/listGridProvinceAll');
  provinceList.value = res.data.data;
};

loadProvinces();

const provinceChange = async () => {
  selectedCity.value = '';
  gridMemberList.value = [];
  if (selectedProvince.value) {
    const res = await axios.get('/gridCity/listGridCityByProvinceId', {
      params: { provinceId: selectedProvince.value.provinceId }
    });
    cityList.value = res.data.data;
  }
};

const cityChange = async () => {
  if (selectedProvince.value && selectedCity.value) {
    const res = await axios.get('/gridMember/listGridMemberByProvinceId', {
      params: { 
        provinceId: selectedProvince.value.provinceId,
        cityId: selectedCity.value.cityId
      }
    });
    gridMemberList.value = res.data.data;
  }
};

const selectGridMember = (item) => {
  sessionStorage.setItem('selectedGridMember', JSON.stringify(item));
  sessionStorage.setItem('selectedCity', JSON.stringify(selectedCity.value));
  router.push('/selectAqi');
};

const goBack = () => router.back();
const logout = () => {
  removeSessionStorage('supervisor');
  router.push('/login');
};
</script>
