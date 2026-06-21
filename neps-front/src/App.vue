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
    font-size: 14px;
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
    height: 60px;
    background-color: #FFF;
    color: #555;
    position: fixed;
    left: 0;
    top: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 0 16px;
    z-index: 100;
}
.header-box .top-ban{
    width: 100%;
    height: 60px;
}
.header-box img{
    display: block;
    width: 100%;
}
.header-box i{
    font-size: 24px;
    cursor: pointer;
}
.table-form{
    width: 100%;
    margin: 0 auto;
    margin-top: 15px;
    border-collapse: collapse;
    font-size: 14px;
}
.table-form tr td{
    height: 40px;
    padding: 5px;
}
.table-form input,.table-form select,.table-form textarea{
    background-color: #CFE8E9;
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 8px;
    width: 100%;
    box-sizing: border-box;
    font-size: 14px;
}
.table-form tr td:first-child{
    color: #777;
    font-weight: 700;
    width: 100px;
}
.btn{
    width: 100%;
    height: 45px;
    background-color: #70B0BC;
    margin-top: 15px;
    border-radius: 6px;
    color: #FFF;
    font-size: 16px;
    text-align: center;
    line-height: 45px;
    letter-spacing: 2px;
    font-weight: 500;
    user-select: none;
    cursor: pointer;
    border: none;
}
.btn:hover{
    background-color: #5a9aaa;
}

/* 移动端适配 */
@media (max-width: 767px) {
  .wrapper{
    width: 100%;
  }
  .table-form{
    width: 90%;
    font-size: 16px;
  }
  .table-form tr td{
    height: 50px;
  }
  .btn{
    height: 50px;
    font-size: 18px;
    line-height: 50px;
  }
}
</style>
