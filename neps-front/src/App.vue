<template>
  <router-view/>
</template>

<script setup>
import axios from 'axios';
import { provide } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

axios.defaults.baseURL = 'http://localhost:8080/nepm/';

// 请求拦截器
axios.interceptors.request.use(
  config => {
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
axios.interceptors.response.use(
  response => {
    const res = response.data;
    // 如果返回的 code 不是 200，统一处理为错误
    if (res.code && res.code !== 200) {
      alert(res.message || '请求失败');
      return Promise.reject(new Error(res.message || '请求失败'));
    }
    return response;
  },
  error => {
    // HTTP 错误状态码处理
    if (error.response) {
      const status = error.response.status;
      switch (status) {
        case 401:
          alert('未登录或登录已过期，请重新登录');
          sessionStorage.clear();
          router.push('/login');
          break;
        case 403:
          alert('没有权限访问');
          break;
        case 404:
          alert('请求的资源不存在');
          break;
        case 500:
          alert('服务器内部错误');
          break;
        default:
          alert(`请求失败：${status}`);
      }
    } else if (error.message.includes('timeout')) {
      alert('请求超时，请稍后重试');
    } else if (error.message.includes('Network Error')) {
      alert('网络错误，请检查网络连接');
    } else {
      alert('请求失败，请重试');
    }
    return Promise.reject(error);
  }
);

provide('axios', axios);
</script>

<style>
html,body,div,span,h1,h2,h3,h4,h5,h6,ul,ol,li,p{
    margin: 0;
    padding: 0;
}
html,body,#app{
    width: 100%;
    height: 100%;
    font-family: "微软雅黑";
}
ul,ol{
    list-style: none;
}
a{
    text-decoration: none;
}
.wrapper{
    width: 100%;
    height: 100%;
    background-color: #DEEEF0;
}
.header-box{
    width: 100%;
}
.header-box header{
    width: 100%;
    height: 15.7vw;
    background-color: #FFF;
    color: #555;
    position: fixed;
    left: 0;
    top: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 0 3.6vw;
}
.header-box .top-ban{
    width: 100%;
    height: 15.7vw;
}
.header-box img{
    display: block;
    width: 100%;
}
.header-box i{
    font-size: 8vw;
}
.table-form{
    width: 86vw;
    margin: 0 auto;
    margin-top: 5vw;
    border-collapse: collapse;
    font-size: 4.2vw;
}
.table-form tr td{
    height: 12vw;
}
.table-form input {
  width: 100%;
  height: 8vw;
  min-height: 36px;
  box-sizing: border-box;
  background-color: #FAFAFA;
  border: 2px solid #e7e7e7;
  border-radius: 8px;
  padding: 0 12px;
  font-size: 15px;
  outline: none;
}
.table-form tr td:first-child{
    color: #777;
    font-weight: 700;
}
.btn{
  width: 100%;
  height: 54px;
  border: none;
  border-radius: 12px;
  background: #74bfd0;
  color: #fff;
  font-size: 20px;
  font-weight: bold;
  letter-spacing: 4px;
  cursor: pointer;
  margin: 10px 0px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* ===== 视图通用类 ===== */
.view-container { width: 86vw; margin: 0 auto; margin-top: 5vw; }
.view-card { background-color: #FFF; padding: 4vw; border-radius: 2vw; margin-bottom: 3vw; }
.view-card-title { font-weight: bold; margin-bottom: 3vw; }
.view-text-lg { font-size: 5vw; font-weight: bold; }
.view-text-md { font-size: 3.5vw; }
.view-text-sm { font-size: 3vw; }
.view-empty { text-align: center; color: #999; padding: 10vw; }
.view-flex-between { display: flex; justify-content: space-between; align-items: center; }
.view-mb-2 { margin-bottom: 2vw; }

/* ===== 桌面端适配 ===== */
@media (min-width: 768px) {
  body { background: #d0d0d0; }
  #app { max-width: 450px; margin: 0 auto; box-shadow: 0 0 30px rgba(0,0,0,0.15); overflow: hidden; }
  .wrapper { border-radius: 0; }
  .header-box header { max-width: 450px; left: 50%; transform: translateX(-50%); height: 60px; padding: 0 16px; }
  .header-box .top-ban { height: 60px; }
  .header-box i { font-size: 24px; }
  .table-form { width: 400px; margin-top: 20px; font-size: 16px; }
  .table-form tr td { height: 48px; }
  .btn { height: 48px; margin-top: 20px; border-radius: 8px; font-size: 18px; line-height: 48px; letter-spacing: 4px; }
  .view-container { width: 400px; margin-top: 20px; }
  .view-card { padding: 16px; border-radius: 8px; margin-bottom: 12px; }
  .view-card-title { margin-bottom: 12px; }
  .view-text-lg { font-size: 20px; }
  .view-text-md { font-size: 14px; }
  .view-text-sm { font-size: 12px; }
  .view-empty { padding: 40px; }
  .view-mb-2 { margin-bottom: 8px; }
}
</style>
