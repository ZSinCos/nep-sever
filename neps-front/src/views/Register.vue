<template>
  <div class="auth-page register-page">
    <HeaderBox title="注册" :showLogout="false" />

    <HeroBanner
        bgImage="../assets/login-banner.png"
        slogan="../assets/register-slogan.png"
        sloganAlt="保护环境人人有责"
    />

    <div class="auth-bottom register-bottom">
      <div class="auth-card register-card">
        <div class="register-form">
          <div class="register-input-item">
            <label>手机号码</label>
            <input
                type="text"
                v-model="supervisor.telId"
                placeholder="请输入手机号码"
            />
          </div>

          <div class="register-input-item">
            <label>真实姓名</label>
            <input
                type="text"
                v-model="supervisor.supervisorName"
                placeholder="真实姓名便于我们联系您"
            />
          </div>

          <div class="register-input-item">
            <label>密码</label>
            <input
                type="password"
                v-model="supervisor.supervisorPassword"
                placeholder="请输入密码"
            />
          </div>

          <div class="register-input-item">
            <label>确认密码</label>
            <input
                type="password"
                v-model="confirmPassword"
                placeholder="请再次输入密码"
            />
          </div>

          <div class="register-input-item">
            <label>省份</label>
            <CustomSelect
                v-model="supervisor.province"
                :options="provinceOptions"
                label-key="provinceName"
                value-key="provinceName"
                placeholder="请选择省"
                @change="provinceChange"
            />
          </div>

          <div class="register-input-item">
            <label>城市</label>
            <CustomSelect
                v-model="supervisor.city"
                :options="cityOptions"
                label-key="cityName"
                value-key="cityName"
                placeholder="请选择市"
                :disabled="!supervisor.province"
            />
          </div>
        </div>

        <button class="auth-btn register-btn" @click="register">注册</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, inject, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getCurDate } from '../common';
import '../assets/auth.css';
import HeaderBox from '../components/HeaderBox.vue';
import HeroBanner from '../components/HeroBanner.vue';
import CustomSelect from '../components/CustomSelect.vue';

const axios = inject('axios');
const router = useRouter();

const supervisor = ref({
  telId: '',
  supervisorName: '',
  supervisorPassword: '',
  province: '',
  city: '',
  registerDate: ''
});

const confirmPassword = ref('');
const provinceList = ref([]);
const cityList = ref([]);
const provinceIdMap = {};

const provinceOptions = computed(() =>
    provinceList.value.map(item => ({ ...item, label: item.provinceName, value: item.provinceName }))
);

const cityOptions = computed(() =>
    cityList.value.map(item => ({ ...item, label: item.cityName, value: item.cityName }))
);

onMounted(async () => {
  try {
    const res = await axios.get('/gridProvince/listGridProvinceAll');
    provinceList.value = res.data.data || [];
    provinceList.value.forEach(item => {
      provinceIdMap[item.provinceName] = item.provinceId;
    });
  } catch (error) {
    console.error(error);
    alert('省份数据加载失败');
  }
});

const provinceChange = async (val) => {
  supervisor.value.city = '';
  cityList.value = [];

  const provinceId = provinceIdMap[val];
  if (!provinceId) return;

  try {
    const res = await axios.get('/gridCity/listGridCityByProvinceId', {
      params: { provinceId }
    });
    cityList.value = res.data.data || [];
  } catch (error) {
    console.error(error);
    alert('城市数据加载失败');
  }
};

const register = async () => {
  if (!supervisor.value.telId) {
    alert('请输入手机号码');
    return;
  }
  if (!/^1[3-9]\d{9}$/.test(supervisor.value.telId)) {
    alert('手机号码格式不正确');
    return;
  }

  if (!supervisor.value.supervisorName) {
    alert('请输入姓名');
    return;
  }
  if (
      supervisor.value.supervisorName.length < 2 ||
      supervisor.value.supervisorName.length > 20
  ) {
    alert('姓名长度应在2-20个字符之间');
    return;
  }

  if (!supervisor.value.supervisorPassword) {
    alert('请输入密码');
    return;
  }
  if (supervisor.value.supervisorPassword.length < 6) {
    alert('密码长度不能少于6位');
    return;
  }

  if (!confirmPassword.value) {
    alert('请再次输入密码');
    return;
  }
  if (supervisor.value.supervisorPassword !== confirmPassword.value) {
    alert('两次密码不一致');
    return;
  }

  if (!supervisor.value.province) {
    alert('请选择省');
    return;
  }
  if (!supervisor.value.city) {
    alert('请选择市');
    return;
  }

  // 检查手机号是否已注册
  try {
    const checkRes = await axios.get(
        '/supervisor/getSupervisorById/' + supervisor.value.telId
    );
    if (checkRes.data.code === 200 && checkRes.data.data) {
      alert('该手机号码已被注册，请直接登录');
      return;
    }
  } catch (e) {
    // 查不到一般视为未注册，继续走注册
  }

  supervisor.value.registerDate = getCurDate();

  try {
    const res = await axios.post('/supervisor/saveSupervisor', supervisor.value);
    if (res.data.code === 200) {
      alert('注册成功');
      router.push('/login');
    } else {
      alert(res.data.message || '注册失败');
    }
  } catch (error) {
    console.error(error);
    alert('注册失败，请重试');
  }
};
</script>

<style scoped>
/* ===== 注册页特有样式 ===== */

/* 卡片最小高度 */
.register-card {
  min-height: 520px;
}

/* 表单区域 */
.register-form {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

/* 输入框容器 */
.register-input-item {
  display: flex;
  align-items: center;
  min-height: 50px;
  padding: 0 2px;
  box-sizing: border-box;
}


/* 左侧标签 */
.register-input-item label {
  width: 84px;
  flex-shrink: 0;
  font-size: 15px;
  color: #5f6f76;
  font-weight: 600;
  line-height: 1.2;
}


/* 输入框 */
.register-input-item input {
  flex: 1;
  min-height: 43px;
  border: 2px solid #e7e7e7;
  border-radius: 12px;
  background: #fafafa;
  box-sizing: border-box;
  padding: 0 14px;
  font-size: 15px;
  color: #666;
  outline: none;
}

.register-input-item input::placeholder {
  color: #bfc5c8;
}

/* 下拉框高度适配 */
.register-input-item :deep(.cs-trigger) {
  height: 50px;
}

/* 注册按钮上边距 */
.register-btn {
  margin-top: 10px;
  font-weight: bold;
}

/* ===== 响应式适配 ===== */

/* 小屏手机 */
@media (max-width: 420px) {
  .register-card {
    min-height: 480px;
  }

  .register-input-item {
    flex-direction: column;
    align-items: stretch;
    min-height: auto;
    gap: 8px;
  }

  .register-input-item label {
    width: auto;
    font-size: 14px;
    padding-left: 2px;
  }

  .register-input-item input {
    height: 46px;
    font-size: 14px;
  }

  .register-input-item :deep(.cs-trigger) {
    height: 46px;
    font-size: 14px;
    padding-right: 32px;
    padding-left: 12px;
  }

  .register-btn {
    margin-top: 20px;
  }
}

/* 大屏/平板 */
@media (min-width: 768px) {
  .register-card {
    min-height: 560px;
  }

  .register-input-item label {
    width: 96px;
    font-size: 16px;
  }

  .register-input-item input {
    height: 54px;
    font-size: 16px;
  }

  .register-input-item :deep(.cs-trigger) {
    height: 54px;
    font-size: 16px;
  }
}
</style>