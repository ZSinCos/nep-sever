<template>
  <div class="header-box">
    <header>
      <i class="fa fa-arrow-left" @click="goBack"></i>
      <span class="view-text-lg">{{ title }}</span>
      <i v-if="showLogout" class="fa fa-sign-out" @click="logout"></i>
      <span v-else></span>
    </header>
    <div class="top-ban"></div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { removeSessionStorage } from '../common';

const props = defineProps({
  title: { type: String, default: '' },
  showLogout: { type: Boolean, default: true }
});

const router = useRouter();

const goBack = () => router.back();
const logout = () => {
  removeSessionStorage('supervisor');
  router.push('/login');
};
</script>
