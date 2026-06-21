<template>
  <div class="cs-wrapper" ref="wrapperRef">
    <!-- 选择框 -->
    <div class="cs-trigger" :class="{ 'cs-open': isOpen, 'cs-disabled': disabled }" @click="toggle">
      <span :class="selectedLabel ? 'cs-label' : 'cs-placeholder'">
        {{ selectedLabel || placeholder }}
      </span>
      <span class="cs-arrow" :class="{ 'cs-arrow-up': isOpen }"></span>
    </div>

    <!-- 下拉面板 -->
    <Teleport to="body">
      <div v-if="isOpen" class="cs-dropdown" :style="dropdownStyle" ref="dropdownRef">
        <div class="cs-dropdown-inner">
          <div
              v-for="(item, index) in options"
              :key="index"
              class="cs-option"
              :class="{
                'cs-option-selected': item[valueKey] === modelValue,
                'cs-option-disabled': item.disabled
              }"
              @click="selectItem(item)"
          >
            {{ item[labelKey] }}
          </div>
          <div v-if="options.length === 0" class="cs-option cs-option-empty">
            {{ emptyText }}
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted, onBeforeUnmount, nextTick } from 'vue';

const props = defineProps({
  modelValue: { type: [String, Number, Object], default: '' },
  options: { type: Array, default: () => [] },
  placeholder: { type: String, default: '请选择' },
  labelKey: { type: String, default: 'label' },
  valueKey: { type: String, default: 'value' },
  disabled: { type: Boolean, default: false },
  emptyText: { type: String, default: '暂无数据' }
});

const emit = defineEmits(['update:modelValue', 'change']);

const isOpen = ref(false);
const wrapperRef = ref(null);
const dropdownRef = ref(null);
const dropdownStyle = ref({});

const selectedLabel = computed(() => {
  if (!props.modelValue) return '';
  if (typeof props.modelValue === 'object') {
    return props.modelValue[props.labelKey] || '';
  }
  const found = props.options.find(item => item[props.valueKey] === props.modelValue);
  return found ? found[props.labelKey] : '';
});

const toggle = () => {
  if (props.disabled) return;
  isOpen.value ? close() : open();
};

const open = () => {
  isOpen.value = true;
  nextTick(updatePosition);
};

const close = () => {
  isOpen.value = false;
};

const selectItem = (item) => {
  if (item.disabled) return;
  emit('update:modelValue', item[props.valueKey]);
  emit('change', item[props.valueKey]);
  close();
};

const updatePosition = () => {
  if (!wrapperRef.value) return;
  const rect = wrapperRef.value.getBoundingClientRect();
  const viewportH = window.innerHeight;
  const dropdownMaxH = 200;

  let top = rect.bottom + 4;
  let left = rect.left;
  let width = rect.width;

  // 下方空间不足时向上展开
  if (top + dropdownMaxH > viewportH) {
    top = rect.top - 4;
    dropdownStyle.value = {
      position: 'fixed',
      left: left + 'px',
      width: width + 'px',
      maxHeight: Math.min(dropdownMaxH, viewportH * 0.5) + 'px',
      transform: 'translateY(-100%)',
      zIndex: 9999
    };
  } else {
    dropdownStyle.value = {
      position: 'fixed',
      left: left + 'px',
      top: top + 'px',
      width: width + 'px',
      maxHeight: Math.min(dropdownMaxH, viewportH - top - 16) + 'px',
      zIndex: 9999
    };
  }
};

const onClickOutside = (e) => {
  if (wrapperRef.value && !wrapperRef.value.contains(e.target) &&
      dropdownRef.value && !dropdownRef.value.contains(e.target)) {
    close();
  }
};

const onScroll = () => {
  if (isOpen.value) updatePosition();
};

onMounted(() => {
  document.addEventListener('click', onClickOutside, true);
  window.addEventListener('scroll', onScroll, true);
  window.addEventListener('resize', onScroll);
});

onBeforeUnmount(() => {
  document.removeEventListener('click', onClickOutside, true);
  window.removeEventListener('scroll', onScroll, true);
  window.removeEventListener('resize', onScroll);
});

watch(isOpen, (val) => {
  if (val) nextTick(updatePosition);
});
</script>

<style>
/* ===== 自定义下拉框全局样式 ===== */

/* 触发框 */
.cs-wrapper {
  position: relative;
  width: 100%;
  justify-content: center;
}

.cs-trigger {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-height: 100%;
  width: 100%;
  border: 2px solid #e7e7e7;
  border-radius: 12px;
  background-color: #fafafa;
  padding: 0 36px 0 20px;
  cursor: pointer;
  transition: border-color 0.2s, box-shadow 0.2s;
  box-sizing: border-box;
}

.cs-trigger:hover {
  border-color: #c0c0c0;
}

.cs-trigger.cs-open {
  border-color: #74bfd0;
  box-shadow: 0 0 0 3px rgba(116, 191, 208, 0.15);
}

.cs-trigger.cs-disabled {
  opacity: 0.6;
  cursor: not-allowed;
  width: 100%;
}

.cs-label {
  font-size: 15px;
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.cs-placeholder {
  font-size: 15px;
  color: #999;
}

/* 下拉箭头 */
.cs-arrow {
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-top: 6px solid #999;
  transition: transform 0.2s;
  flex-shrink: 0;
  margin-left: 8px;
}

.cs-arrow-up {
  transform: rotate(180deg);
}

/* 下拉面板 */
.cs-dropdown {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.12);
  overflow: hidden;
}

.cs-dropdown-inner {
  max-height: inherit;
  overflow-y: auto;
  overscroll-behavior: contain;
  padding: 4px 0;
}

/* 滚动条美化 */
.cs-dropdown-inner::-webkit-scrollbar {
  width: 6px;
}

.cs-dropdown-inner::-webkit-scrollbar-track {
  background: transparent;
}

.cs-dropdown-inner::-webkit-scrollbar-thumb {
  background: #ccc;
  border-radius: 3px;
}

.cs-dropdown-inner::-webkit-scrollbar-thumb:hover {
  background: #aaa;
}

/* 选项 */
.cs-option {
  padding: 10px 20px;
  font-size: 15px;
  color: #333;
  cursor: pointer;
  transition: background-color 0.15s;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.cs-option:hover {
  background-color: #f0f7f8;
}

.cs-option-selected {
  color: #74bfd0;
  font-weight: 600;
  background-color: #f0f9fb;
}

.cs-option-disabled {
  color: #ccc;
  cursor: not-allowed;
}

.cs-option-disabled:hover {
  background-color: transparent;
}

.cs-option-empty {
  color: #999;
  cursor: default;
  text-align: center;
}

.cs-option-empty:hover {
  background-color: transparent;
}
</style>
