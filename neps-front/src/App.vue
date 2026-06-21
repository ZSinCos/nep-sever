<template>
  <router-view/>
</template>

<script setup>
import axios from 'axios';
import { provide } from 'vue';

axios.defaults.baseURL = 'http://localhost:8080/nepm/';
provide('axios',axios);
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
    background-color: #CFE8E9;
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
.table-form input,.table-form select,.table-form textarea{
    background-color: #CFE8E9;
    border: none;
    outline: none;
}
.table-form tr td:first-child{
    color: #777;
    font-weight: 700;
}
.btn{
    width: 100%;
    height: 12vw;
    background-color: #70B0BC;
    margin-top: 5vw;
    border-radius: 2vw;
    color: #FFF;
    font-size: 5vw;
    text-align: center;
    line-height: 12vw;
    letter-spacing: 1vw;
    font-weight: 500;
    user-select: none;
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
