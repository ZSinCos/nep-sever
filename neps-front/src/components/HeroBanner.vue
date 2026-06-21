<template>
  <div class="hero-banner">
    <div class="hero-banner-bg" :style="{ backgroundImage: `url(${resolvedBg})` }"></div>
    <div v-if="slogan" class="hero-banner-slogan">
      <img :src="resolvedSlogan" :alt="sloganAlt" />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  bgImage: { type: String, default: '' },
  slogan: { type: String, default: '' },
  sloganAlt: { type: String, default: '' }
});

const resolveAsset = (path) => {
  if (!path) return '';
  const fileName = path.split('/').pop();
  return require('../assets/' + fileName);
};

const resolvedBg = computed(() => resolveAsset(props.bgImage));
const resolvedSlogan = computed(() => resolveAsset(props.slogan));
</script>

<style scoped>
.hero-banner {
  flex-shrink: 0;
}

.hero-banner-bg {
  width: 100%;
  height: clamp(180px, 32vh, 280px);
  background-repeat: no-repeat;
  background-position: center top;
  background-size: 100% 100%;
}

.hero-banner-slogan {
  margin-top: 0;
  display: flex;
  justify-content: center;
}

.hero-banner-slogan img {
  width: 100%;
  max-width: 260px;
  display: block;
  object-fit: contain;
}

@media (max-width: 420px) {
  .hero-banner-bg {
    height: clamp(160px, 28vh, 240px);
  }
}

@media (min-width: 768px) {
  .hero-banner-bg {
    height: 300px;
  }
}
</style>
