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
        <el-form
            ref="formRef"
            :model="supervisor"
            :rules="rules"
            label-width="84px"
            class="register-form"
        >
          <el-form-item label="手机号码" prop="telId">
            <el-input
                v-model="supervisor.telId"
                placeholder="请输入手机号码"
            />
          </el-form-item>

          <el-form-item label="真实姓名" prop="supervisorName">
            <el-input
                v-model="supervisor.supervisorName"
                placeholder="真实姓名便于我们联系您"
            />
          </el-form-item>

          <el-form-item label="密码" prop="supervisorPassword">
            <el-input
                v-model="supervisor.supervisorPassword"
                type="password"
                placeholder="请输入密码"
                show-password
            />
          </el-form-item>

          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
                v-model="supervisor.confirmPassword"
                type="password"
                placeholder="请再次输入密码"
                show-password
            />
          </el-form-item>

          <el-form-item label="省份" prop="province">
            <CustomSelect
                v-model="supervisor.province"
                :options="provinceOptions"
                label-key="provinceName"
                value-key="provinceName"
                placeholder="请选择省"
                @change="provinceChange"
            />
          </el-form-item>

          <el-form-item label="城市" prop="city">
            <CustomSelect
                v-model="supervisor.city"
                :options="cityOptions"
                label-key="cityName"
                value-key="cityName"
                placeholder="请选择市"
                :disabled="!supervisor.province"
            />
          </el-form-item>
        </el-form>

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
const formRef = ref(null);

const supervisor = ref({
  telId: '',
  supervisorName: '',
  supervisorPassword: '',
  confirmPassword: '',
  province: '',
  city: '',
  registerDate: ''
});

const provinceList = ref([]);
const cityList = ref([]);
const provinceIdMap = {};

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== supervisor.value.supervisorPassword) {
    callback(new Error('两次密码不一致'));
  } else {
    callback();
  }
};

const rules = {
  telId: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号码格式不正确', trigger: 'blur' }
  ],
  supervisorName: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '姓名长度应在2-20个字符之间', trigger: 'blur' }
  ],
  supervisorPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ],
  province: [
    { required: true, message: '请选择省', trigger: 'change' }
  ],
  city: [
    { required: true, message: '请选择市', trigger: 'change' }
  ]
};

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
  const valid = await formRef.value.validate().catch(() => false);
  if (!valid) return;

  try {
    const checkRes = await axios.get(
        '/supervisor/getSupervisorById/' + supervisor.value.telId,
        { silentError: true }
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
.register-card {
  min-height: 520px;
}

.register-form {
  display: flex;
  flex-direction: column;
}

.register-btn {
  margin-top: 10px;
  font-weight: bold;
}

@media (max-width: 420px) {
  .register-card {
    min-height: 480px;
  }

  .register-btn {
    margin-top: 20px;
  }
}

@media (min-width: 768px) {
  .register-card {
    min-height: 560px;
  }
}
</style>
