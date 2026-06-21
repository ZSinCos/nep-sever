<template>
  <div class="wrapper">
    <HeaderBox title="选择网格" />
    <HeroBanner
        bgImage="../assets/login-banner.png"
        slogan="../assets/register-slogan.png"
        sloganAlt="保护环境人人有责"
    />
    <div class="view-container">
      <div class="view-card">
        <div class="view-card-title">选择网格</div>
        <table class="table-form" style="margin: 0;">
          <tr>
            <td>省</td>
            <td>
              <CustomSelect
                  v-model="selectedProvince"
                  :options="provinceOptions"
                  placeholder="请选择省"
                  @change="provinceChange"
              />
            </td>
          </tr>
          <tr>
            <td>市</td>
            <td>
              <CustomSelect
                  v-model="selectedCity"
                  :options="cityOptions"
                  placeholder="请选择市"
                  :disabled="!selectedProvince"
              />
            </td>
          </tr>
          <tr>
            <td>地址</td>
            <td>
              <textarea v-model="selectedAddress" placeholder="请输入具体地址"></textarea>
            </td>
          </tr>
        </table>
      </div>
      <div class="btn" @click="submitAddress">下一步</div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, inject } from 'vue';
import { useRouter } from 'vue-router';
import HeaderBox from '../components/HeaderBox.vue';
import HeroBanner from '../components/HeroBanner.vue';
import CustomSelect from '../components/CustomSelect.vue';

const axios = inject('axios');
const router = useRouter();

const selectedProvince = ref('');
const selectedCity = ref('');
const selectedAddress = ref('');
const provinceList = ref([]);
const cityList = ref([]);

const provinceOptions = computed(() =>
    provinceList.value.map(item => ({ label: item.provinceName, value: item.provinceName, provinceId: item.provinceId }))
);

const cityOptions = computed(() =>
    cityList.value.map(item => ({ label: item.cityName, value: item.cityName, cityId: item.cityId }))
);

const loadProvinces = async () => {
  const res = await axios.get('/gridProvince/listGridProvinceAll');
  provinceList.value = res.data.data;
};

loadProvinces();

const provinceChange = async (val) => {
  selectedCity.value = '';
  if (val) {
    const province = provinceList.value.find(item => item.provinceName === val);
    if (province) {
      const res = await axios.get('/gridCity/listGridCityByProvinceId', {
        params: { provinceId: province.provinceId }
      });
      cityList.value = res.data.data;
    }
  } else {
    cityList.value = [];
  }
};

const submitAddress = () => {
  if (!selectedProvince.value) {
    alert('请选择省');
    return;
  }
  if (!selectedCity.value) {
    alert('请选择市');
    return;
  }
  if (!selectedAddress.value.trim()) {
    alert('请输入具体地址');
    return;
  }

  const province = provinceList.value.find(item => item.provinceName === selectedProvince.value);
  const city = cityList.value.find(item => item.cityName === selectedCity.value);

  sessionStorage.setItem('selectedProvince', JSON.stringify(province));
  sessionStorage.setItem('selectedCity', JSON.stringify(city));
  sessionStorage.setItem('selectedAddress', selectedAddress.value.trim());
  router.push('/selectAqi');
};
</script>

<style scoped>
.table-form {
  width: 100%;
  margin: 0;
  border-collapse: separate;
  border-spacing: 0 2vw;
}

.table-form :deep(.cs-trigger) {
  height: 12vw;
  min-height: 48px;
}

.table-form textarea {
  width: 100%;
  height: 25vw;
  min-height: 100px;
  box-sizing: border-box;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 2vw;
  padding: 3vw;
  font-size: 4vw;
  color: #333;
  outline: none;
  resize: vertical;
  vertical-align: top;
}

.table-form textarea:focus {
  border-color: #74bfd0;
}


@media (min-width: 768px) {
  .table-form {
    border-spacing: 0 16px;
  }
  .table-form :deep(.cs-trigger) {
    height: 48px;
    min-height: 48px;
  }
  .table-form textarea {
    height: 120px;
    min-height: 120px;
    font-size: 14px;
    padding: 12px;
  }
}
</style>
