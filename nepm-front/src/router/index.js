import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Index from '../views/Index.vue'
import Home from '../views/Home.vue'

import FeedbackList from '../views/feedback/FeedbackList.vue'
import StatisticsList from '../views/feedback/StatisticsList.vue'
import StatisticsInfo from '../views/feedback/StatisticsInfo.vue'
import FeedbackInfo from '../views/feedback/FeedbackInfo.vue'
import AssignFeedback from '../views/feedback/AssignFeedback.vue'
import ProvinceItemTotalStatis from '../views/statistics/ProvinceItemTotalStatis.vue'
import AqiDistributeTotalStatis from '../views/statistics/AqiDistributeTotalStatis.vue'
import AqiTrendTotalStatis from '../views/statistics/AqiTrendTotalStatis.vue'
import OtherTotalStatis from '../views/statistics/OtherTotalStatis.vue'

const routes = [
  { path: '/', name: 'home', component: Login },
  { path: '/login', name: 'Login', component: Login },
  {
    path: '/index',
    name: 'Index',
    component: Index,
    children: [
      { path: '/index', component: Home },
      { path: '/index/feedbackList', component: FeedbackList },
      { path: '/index/statisticsList', component: StatisticsList },
      { path: '/index/statisticsInfo', component: StatisticsInfo },
      { path: '/index/provinceItemTotalStatis', component: ProvinceItemTotalStatis },
      { path: '/index/aqiDistributeTotalStatis', component: AqiDistributeTotalStatis },
      { path: '/index/aqiTrendTotalStatis', component: AqiTrendTotalStatis },
      { path: '/index/otherTotalStatis', component: OtherTotalStatis },
      { path: '/index/feedbackInfo', component: FeedbackInfo },
      { path: '/index/assignFeedback', component: AssignFeedback }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
