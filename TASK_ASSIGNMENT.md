# 东软环保公众监督系统 - 团队任务分配

## 团队成员

| 角色 | 成员 | 负责模块 |
|------|------|----------|
| 队长 | [你的名字] | 后端服务、数据库设计、项目统筹 |
| 成员1 | [成员1名字] | NEPS公众监督员端 |
| 成员2 | [成员2名字] | NEPG网格员端 |
| 成员3 | [成员3名字] | NEPM系统管理端 |
| 成员4 | [成员4名字] | NEPV决策者端 |

---

## 任务详情

### 队长任务 - 后端服务与统筹

**负责模块：** `nep-server/`、`sql/`

| 任务 | 说明 | 文件 |
|------|------|------|
| 数据库设计 | 8张表的设计与创建 | sql/nep_db.sql |
| 实体类 | 8个实体类 | entity/*.java |
| Mapper层 | 8个Mapper接口+XML | mapper/*.java, mapper/*.xml |
| Service层 | 8个Service接口+实现 | service/*.java, service/impl/*.java |
| Controller层 | 8个Controller | controller/*.java |
| 配置类 | 跨域配置、启动类 | config/*.java |
| 项目统筹 | 代码整合、问题解决、进度管理 | - |

**API接口：** 21个

---

### 成员1任务 - NEPS公众监督员端

**负责模块：** `neps-front/`

| 任务 | 说明 | 文件 |
|------|------|------|
| 项目搭建 | Vue CLI项目配置 | vue.config.js, package.json |
| 登录页面 | 公众监督员登录 | views/Login.vue |
| 注册页面 | 公众监督员注册 | views/Register.vue |
| 网格分配 | 选择省/市/网格员 | views/SelectGrid.vue |
| 预估AQI | 选择AQI级别、提交反馈 | views/SelectAqi.vue |
| 反馈列表 | 查看历史反馈 | views/FeedbackList.vue |
| 路由配置 | 页面路由+登录守卫 | router/index.js |
| 公共工具 | 日期、存储等工具函数 | common.js |

**API对接：**
- /supervisor/getSupervisorByIdByPass（登录）
- /supervisor/saveSupervisor（注册）
- /supervisor/getSupervisorById（验证手机号）
- /aqiFeedback/saveAqiFeedback（提交反馈）
- /aqiFeedback/listAqiFeedbackByTelId（反馈列表）
- /aqi/listAqiAll（AQI级别）
- /gridProvince/listGridProvinceAll（省列表）
- /gridCity/listGridCityByProvinceId（市列表）
- /gridMember/listGridMemberByProvinceId（网格员）

---

### 成员2任务 - NEPG网格员端

**负责模块：** `nepg-front/`

| 任务 | 说明 | 文件 |
|------|------|------|
| 项目搭建 | Vue CLI项目配置 | vue.config.js, package.json |
| 登录页面 | 网格员登录 | views/Login.vue |
| 反馈列表 | 查看指派的反馈 | views/FeedbackList.vue |
| 反馈详情 | 确认AQI数据 | views/FeedbackInfo.vue |
| 路由配置 | 页面路由+登录守卫 | router/index.js |
| 公共工具 | 日期、存储等工具函数 | common.js |

**API对接：**
- /gridMember/getGridMemberByCodeByPass（登录）
- /aqiFeedback/listAqiFeedbackByGmId（反馈列表）
- /aqiFeedback/getAqiFeedbackById（反馈详情）
- /aqi/listAqiAll（AQI级别）
- /statistics/saveStatistics（保存确认数据）
- /aqiFeedback/updateAqiFeedbackState（更新状态）

---

### 成员3任务 - NEPM系统管理端

**负责模块：** `nepm-front/`

| 任务 | 说明 | 文件 |
|------|------|------|
| 项目搭建 | Vue CLI项目配置 | vue.config.js, package.json |
| 依赖安装 | Element Plus, ECharts | package.json |
| 登录页面 | 管理员登录 | views/Login.vue |
| 主页面 | 侧边栏布局 | views/Index.vue |
| 反馈列表 | 分页、搜索、指派 | views/feedback/FeedbackList.vue |
| 反馈详情 | 查看反馈详情 | views/feedback/FeedbackInfo.vue |
| 指派页面 | 指派网格员 | views/feedback/AssignFeedback.vue |
| 确认列表 | 查看确认数据 | views/feedback/StatisticsList.vue |
| 确认详情 | 查看确认详情 | views/feedback/StatisticsInfo.vue |
| 省AQI统计 | 柱状图 | views/statistics/ProvinceItemTotalStatis.vue |
| AQI分布 | 饼图 | views/statistics/AqiDistributeTotalStatis.vue |
| AQI趋势 | 折线图 | views/statistics/AqiTrendTotalStatis.vue |
| 其他统计 | 数据卡片 | views/statistics/OtherTotalStatis.vue |
| 路由配置 | 嵌套路由 | router/index.js |
| Vuex配置 | 状态管理 | store/index.js |

**API对接：**
- /admins/getAdminsByCode（登录）
- /aqiFeedback/listAqiFeedbackPage（反馈分页）
- /aqiFeedback/getAqiFeedbackById（反馈详情）
- /aqiFeedback/updateAqiFeedbackAssign（指派）
- /statistics/listStatisticsPage（统计分页）
- /statistics/getStatisticsById（统计详情）
- /statistics/listProvinceItemTotalStatis（省统计）
- /statistics/listAqiDistributeTotalStatis（分布统计）
- /statistics/listAqiTrendTotalStatis（趋势统计）
- /statistics/getAqiCount（总数）
- /statistics/getAqiGoodCount（良好数）

---

### 成员4任务 - NEPV决策者端

**负责模块：** `nepv-front/`

| 任务 | 说明 | 文件 |
|------|------|------|
| 项目搭建 | Vue CLI项目配置 | vue.config.js, package.json |
| 依赖安装 | ECharts | package.json |
| 大屏页面 | 数据可视化展示 | views/Index.vue |
| 数据卡片 | AQI总数、良好数、良好率 | - |
| 柱状图 | 各省AQI超标统计 | - |
| 饼图 | AQI等级分布 | - |
| 折线图 | 12个月AQI趋势 | - |
| 条形图 | 各省排名 | - |
| 路由配置 | 路由配置 | router/index.js |

**API对接：**
- /statistics/getAqiCount（AQI总数）
- /statistics/getAqiGoodCount（AQI良好数）
- /statistics/listAqiDistributeTotalStatis（分布统计）
- /statistics/listProvinceItemTotalStatis（省统计）
- /statistics/listAqiTrendTotalStatis（趋势统计）

---

## 协作规范

### 1. Git分支管理
```
main          - 主分支（队长管理）
dev           - 开发分支
dev-member1   - 成员1开发分支
dev-member2   - 成员2开发分支
dev-member3   - 成员3开发分支
dev-member4   - 成员4开发分支
```

### 2. 提交规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试
chore: 构建/工具
```

### 3. 开发流程
1. 从 main 分支拉取最新代码
2. 创建自己的开发分支
3. 完成开发后提交代码
4. 向 main 分支发起 Pull Request
5. 队长审核后合并

### 4. 命名规范
- Vue组件：PascalCase（如 Login.vue）
- JavaScript函数：camelCase（如 getCurDate）
- CSS类名：kebab-case（如 header-box）
- Java类：PascalCase（如 AdminsController）
- Java方法：camelCase（如 getAdminsByCode）

---

## 开发环境

| 工具 | 版本 |
|------|------|
| JDK | 21 |
| Maven | 3.9+ |
| MySQL | 8.0 |
| Node.js | 16+ |
| npm | 8+ |
| IntelliJ IDEA | 2026+ |
| VS Code | 最新版 |

---

## 项目启动顺序

1. 启动 MySQL 服务
2. 执行 `sql/nep_db.sql` 创建数据库
3. 启动后端服务 `nep-server`
4. 启动前端项目（可同时启动多个）

---

## 沟通机制

- 每日站会汇报进度
- 遇到问题及时在群内沟通
- 代码冲突由队长协调解决
- 每周进行一次代码评审

---

## 截止时间

| 阶段 | 截止日期 | 交付物 |
|------|----------|--------|
| 环境搭建 | [日期] | 开发环境就绪 |
| 后端开发 | [日期] | API接口完成 |
| 前端开发 | [日期] | 页面功能完成 |
| 联调测试 | [日期] | 功能测试通过 |
| 项目答辩 | [日期] | 项目演示 |
