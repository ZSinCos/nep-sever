# 东软环保公众监督系统 - 团队任务分配

## 团队成员

| 角色 | 成员 | 负责模块 | 预计工作量 |
|------|------|----------|------------|
| 队长 | [你的名字] | 后端服务、数据库、统筹 | 35% |
| 成员1 | [成员1名字] | NEPS公众监督员端 | 15% |
| 成员2 | [成员2名字] | NEPG网格员端 | 12% |
| 成员3 | [成员3名字] | NEPM系统管理端 | 25% |
| 成员4 | [成员4名字] | NEPV决策者端 | 13% |

---

## 队长任务 - 后端服务与统筹

**负责目录：** `nep-server/`、`sql/`

### 1. 数据库设计与创建

| 文件 | 说明 |
|------|------|
| sql/nep_db.sql | 数据库脚本（8张表） |

**数据表：**

| 表名 | 说明 | 字段数 |
|------|------|--------|
| admins | 系统管理员表 | 3 |
| supervisor | 公众监督员表 | 6 |
| aqi | 空气质量指数级别表 | 6 |
| grid_province | 省区域表 | 2 |
| grid_city | 市区域表 | 3 |
| grid_member | 网格员表 | 7 |
| aqi_feedback | 反馈表 | 15 |
| statistics | 统计数据表 | 13 |

### 2. 实体类（8个）

| 文件 | 说明 | 对应表 |
|------|------|--------|
| entity/Admins.java | 管理员实体 | admins |
| entity/Supervisor.java | 监督员实体 | supervisor |
| entity/Aqi.java | AQI级别实体 | aqi |
| entity/GridProvince.java | 省实体 | grid_province |
| entity/GridCity.java | 市实体 | grid_city |
| entity/GridMember.java | 网格员实体 | grid_member |
| entity/AqiFeedback.java | 反馈实体 | aqi_feedback |
| entity/Statistics.java | 统计实体 | statistics |

### 3. Mapper接口 + XML（8组）

| 接口 | XML | 方法数 |
|------|-----|--------|
| mapper/AdminsMapper.java | mapper/AdminsMapper.xml | 1 |
| mapper/SupervisorMapper.java | mapper/SupervisorMapper.xml | 3 |
| mapper/AqiMapper.java | mapper/AqiMapper.xml | 1 |
| mapper/GridProvinceMapper.java | mapper/GridProvinceMapper.xml | 1 |
| mapper/GridCityMapper.java | mapper/GridCityMapper.xml | 1 |
| mapper/GridMemberMapper.java | mapper/GridMemberMapper.xml | 2 |
| mapper/AqiFeedbackMapper.java | mapper/AqiFeedbackMapper.xml | 8 |
| mapper/StatisticsMapper.java | mapper/StatisticsMapper.xml | 9 |

### 4. Service层（8组）

| 接口 | 实现 | 方法数 |
|------|------|--------|
| service/AdminsService.java | impl/AdminsServiceImpl.java | 1 |
| service/SupervisorService.java | impl/SupervisorServiceImpl.java | 3 |
| service/AqiService.java | impl/AqiServiceImpl.java | 1 |
| service/GridProvinceService.java | impl/GridProvinceServiceImpl.java | 1 |
| service/GridCityService.java | impl/GridCityServiceImpl.java | 1 |
| service/GridMemberService.java | impl/GridMemberServiceImpl.java | 2 |
| service/AqiFeedbackService.java | impl/AqiFeedbackServiceImpl.java | 8 |
| service/StatisticsService.java | impl/StatisticsServiceImpl.java | 9 |

### 5. Controller层（8个）

| 文件 | API数量 |
|------|---------|
| controller/AdminsController.java | 1 |
| controller/SupervisorController.java | 3 |
| controller/AqiController.java | 1 |
| controller/GridProvinceController.java | 1 |
| controller/GridCityController.java | 1 |
| controller/GridMemberController.java | 2 |
| controller/AqiFeedbackController.java | 7 |
| controller/StatisticsController.java | 8 |

### 6. 配置类

| 文件 | 说明 |
|------|------|
| config/CorsConfig.java | 跨域配置 |
| NepServerApplication.java | 启动类 |
| application.yaml | 数据库配置 |
| pom.xml | Maven依赖 |
| dto/PageRequestDto.java | 分页请求 |
| dto/PageResponseDto.java | 分页响应 |

### 7. 统筹任务

- 代码整合与冲突解决
- 技术问题解答
- 进度跟踪与协调
- 代码审查

**API接口总数：21个**

---

## 成员1任务 - NEPS公众监督员端

**负责目录：** `neps-front/`

### 1. 项目配置文件

| 文件 | 说明 |
|------|------|
| vue.config.js | 端口配置（8081） |
| package.json | 依赖管理 |
| src/main.js | 入口文件、路由守卫 |
| src/App.vue | 根组件、axios配置 |
| src/common.js | 公共工具函数 |
| src/router/index.js | 路由配置（5个路由） |

### 2. 页面组件（5个）

**Login.vue - 登录页面**
```
功能：
- 手机号码输入框
- 密码输入框
- 登录按钮
- 注册跳转链接

逻辑：
- 调用 /supervisor/getSupervisorByIdByPass 接口
- 登录成功存储 sessionStorage
- 跳转到 /selectGrid

涉及API：
- /supervisor/getSupervisorByIdByPass
```

**Register.vue - 注册页面**
```
功能：
- 手机号码输入框
- 姓名输入框
- 密码输入框
- 确认密码输入框
- 省下拉选择框
- 市下拉选择框
- 注册按钮
- 返回按钮

逻辑：
- 页面加载时获取省列表
- 选择省后联动加载市列表
- 验证两次密码是否一致
- 调用 /supervisor/saveSupervisor 接口
- 注册成功跳转登录页

涉及API：
- /gridProvince/listGridProvinceAll
- /gridCity/listGridCityByProvinceId
- /supervisor/saveSupervisor
- /supervisor/getSupervisorById（验证手机号）
```

**SelectGrid.vue - 网格分配页面**
```
功能：
- 省下拉选择框
- 市下拉选择框
- 网格员列表展示
- 网格员选择按钮
- 退出登录按钮

逻辑：
- 页面加载时获取省列表
- 选择省后联动加载市列表
- 选择市后加载网格员列表
- 选择网格员后跳转到 /selectAqi
- 将选中的网格员和城市存入 sessionStorage

涉及API：
- /gridProvince/listGridProvinceAll
- /gridCity/listGridCityByProvinceId
- /gridMember/listGridMemberByProvinceId
```

**SelectAqi.vue - 预估AQI页面**
```
功能：
- AQI级别选择（6个级别，带颜色）
- 详细地址输入框
- 反馈内容文本框
- 提交反馈按钮
- 退出登录按钮

逻辑：
- 页面加载时获取AQI级别列表
- 选择AQI级别（高亮显示）
- 填写地址和内容
- 调用 /aqiFeedback/saveAqiFeedback 接口
- 提交成功跳转到 /feedbackList

涉及API：
- /aqi/listAqiAll
- /aqiFeedback/saveAqiFeedback
```

**FeedbackList.vue - 历史反馈列表页面**
```
功能：
- 反馈列表展示
- 状态标签（待指派/已指派/已确认）
- 区域、地址、时间显示
- 退出登录按钮

逻辑：
- 页面加载时获取当前用户的反馈列表
- 调用 /aqiFeedback/listAqiFeedbackByTelId 接口
- 根据状态显示不同颜色标签

涉及API：
- /aqiFeedback/listAqiFeedbackByTelId
```

### 3. 涉及API汇总（9个）

| API | 方法 | 用途 |
|-----|------|------|
| /supervisor/getSupervisorByIdByPass | GET | 监督员登录 |
| /supervisor/saveSupervisor | POST | 监督员注册 |
| /supervisor/getSupervisorById | GET | 验证手机号 |
| /gridProvince/listGridProvinceAll | GET | 获取省列表 |
| /gridCity/listGridCityByProvinceId | GET | 获取市列表 |
| /gridMember/listGridMemberByProvinceId | GET | 获取网格员列表 |
| /aqi/listAqiAll | GET | 获取AQI级别 |
| /aqiFeedback/saveAqiFeedback | POST | 提交反馈 |
| /aqiFeedback/listAqiFeedbackByTelId | GET | 查询反馈列表 |

---

## 成员2任务 - NEPG网格员端

**负责目录：** `nepg-front/`

### 1. 项目配置文件

| 文件 | 说明 |
|------|------|
| vue.config.js | 端口配置（8083） |
| package.json | 依赖管理 |
| src/main.js | 入口文件、路由守卫 |
| src/App.vue | 根组件、axios配置 |
| src/common.js | 公共工具函数 |
| src/router/index.js | 路由配置（3个路由） |

### 2. 页面组件（3个）

**Login.vue - 登录页面**
```
功能：
- 网格员编码输入框
- 密码输入框
- 登录按钮

逻辑：
- 调用 /gridMember/getGridMemberByCodeByPass 接口
- 登录成功存储 sessionStorage
- 跳转到 /feedbackList

涉及API：
- /gridMember/getGridMemberByCodeByPass
```

**FeedbackList.vue - 反馈列表页面**
```
功能：
- 反馈列表展示
- 状态标签显示
- 区域、地址、时间显示
- 点击跳转详情
- 退出登录按钮

逻辑：
- 页面加载时获取指派给当前网格员的反馈
- 调用 /aqiFeedback/listAqiFeedbackByGmId 接口
- 将选中的反馈存入 sessionStorage
- 跳转到 /feedbackInfo

涉及API：
- /aqiFeedback/listAqiFeedbackByGmId
```

**FeedbackInfo.vue - 反馈详情页面**
```
功能：
- 反馈信息展示（区域、地址、AQI、时间、内容）
- 已指派状态：显示确认表单
  - AQI级别下拉选择
  - PM2.5输入框
  - PM10输入框
  - SO2输入框
  - NO2输入框
  - CO输入框
  - O3输入框
  - 备注文本框
  - 提交按钮
- 已确认状态：显示"已完成确认"
- 返回按钮
- 退出登录按钮

逻辑：
- 从 sessionStorage 获取反馈详情
- 页面加载时获取AQI级别列表
- 已指派状态可填写确认数据
- 调用 /statistics/saveStatistics 保存统计数据
- 调用 /aqiFeedback/updateAqiFeedbackState 更新状态为已确认
- 成功后跳转回列表

涉及API：
- /aqi/listAqiAll
- /statistics/saveStatistics
- /aqiFeedback/updateAqiFeedbackState
```

### 3. 涉及API汇总（5个）

| API | 方法 | 用途 |
|-----|------|------|
| /gridMember/getGridMemberByCodeByPass | GET | 网格员登录 |
| /aqiFeedback/listAqiFeedbackByGmId | GET | 查询指派的反馈 |
| /aqi/listAqiAll | GET | 获取AQI级别 |
| /statistics/saveStatistics | POST | 保存确认数据 |
| /aqiFeedback/updateAqiFeedbackState | POST | 更新反馈状态 |

---

## 成员3任务 - NEPM系统管理端

**负责目录：** `nepm-front/`

### 1. 项目配置文件

| 文件 | 说明 |
|------|------|
| vue.config.js | 端口配置（8082） |
| package.json | 依赖管理（Element Plus, ECharts） |
| src/main.js | 入口文件、Element Plus配置、路由守卫 |
| src/App.vue | 根组件、axios配置、ElMessage封装 |
| src/common.js | 公共工具函数 |
| src/router/index.js | 路由配置（嵌套路由） |
| src/store/index.js | Vuex状态管理 |

### 2. 页面组件（10个）

**Login.vue - 登录页面**
```
功能：
- 管理员编码输入框（Element Plus Input）
- 密码输入框（Element Plus Input）
- 登录按钮（Element Plus Button）
- 渐变背景设计

逻辑：
- 调用 /admins/getAdminsByCode 接口
- 登录成功存储 sessionStorage
- 跳转到 /index
- 使用 ElMessage 提示

涉及API：
- /admins/getAdminsByCode
```

**Index.vue - 主页面**
```
功能：
- 顶部导航栏（蓝色背景）
- 左侧边栏菜单（Element Plus Menu）
  - 首页
  - 反馈管理（子菜单）
    - 反馈列表
    - 确认列表
  - 统计分析（子菜单）
    - 省AQI统计
    - AQI分布统计
    - AQI趋势统计
    - 其他统计
- 右侧内容区（router-view）
- 退出登录按钮

组件：
- el-container, el-header, el-aside, el-main
- el-menu, el-sub-menu, el-menu-item
```

**FeedbackList.vue - 反馈列表页面**
```
功能：
- 搜索框（关键字搜索）
- 数据表格（Element Plus Table）
- 状态标签（待指派/已指派/已确认）
- 操作按钮：详情、指派
- 分页组件（Element Plus Pagination）

涉及API：
- /aqiFeedback/listAqiFeedbackPage
```

**FeedbackInfo.vue - 反馈详情页面**
```
功能：
- 反馈信息展示（Element Plus Descriptions）
- 返回按钮

组件：
- el-card, el-descriptions, el-descriptions-item
- el-tag, el-button
```

**AssignFeedback.vue - 指派页面**
```
功能：
- 反馈信息展示
- 网格员下拉选择框（Element Plus Select）
- 确认指派按钮
- 取消按钮

涉及API：
- /gridMember/listGridMemberByProvinceId
- /aqiFeedback/updateAqiFeedbackAssign
```

**StatisticsList.vue - 确认列表页面**
```
功能：
- 搜索框
- 数据表格
- 操作按钮：详情
- 分页组件

涉及API：
- /statistics/listStatisticsPage
```

**StatisticsInfo.vue - 确认详情页面**
```
功能：
- 统计信息展示
- 返回按钮
```

**ProvinceItemTotalStatis.vue - 省AQI统计页面**
```
功能：
- 柱状图展示（ECharts）
- 双系列：超标次数、总次数

涉及API：
- /statistics/listProvinceItemTotalStatis
```

**AqiDistributeTotalStatis.vue - AQI分布统计页面**
```
功能：
- 饼图展示（ECharts）

涉及API：
- /statistics/listAqiDistributeTotalStatis
```

**AqiTrendTotalStatis.vue - AQI趋势统计页面**
```
功能：
- 折线图展示（ECharts）

涉及API：
- /statistics/listAqiTrendTotalStatis
```

**OtherTotalStatis.vue - 其他统计页面**
```
功能：
- 四个数据卡片

涉及API：
- /statistics/getAqiCount
- /statistics/getAqiGoodCount
```

### 3. 涉及API汇总（11个）

| API | 方法 | 用途 |
|-----|------|------|
| /admins/getAdminsByCode | GET | 管理员登录 |
| /aqiFeedback/listAqiFeedbackPage | GET | 反馈分页查询 |
| /aqiFeedback/getAqiFeedbackById | GET | 反馈详情 |
| /aqiFeedback/updateAqiFeedbackAssign | POST | 指派网格员 |
| /gridMember/listGridMemberByProvinceId | GET | 网格员列表 |
| /statistics/listStatisticsPage | GET | 统计分页查询 |
| /statistics/getStatisticsById | GET | 统计详情 |
| /statistics/listProvinceItemTotalStatis | GET | 省AQI统计 |
| /statistics/listAqiDistributeTotalStatis | GET | AQI分布统计 |
| /statistics/listAqiTrendTotalStatis | GET | AQI趋势统计 |
| /statistics/getAqiCount | GET | AQI总数 |
| /statistics/getAqiGoodCount | GET | AQI良好数 |

---

## 成员4任务 - NEPV决策者端

**负责目录：** `nepv-front/`

### 1. 项目配置文件

| 文件 | 说明 |
|------|------|
| vue.config.js | 端口配置（8084） |
| package.json | 依赖管理（ECharts） |
| src/main.js | 入口文件 |
| src/App.vue | 根组件 |
| src/router/index.js | 路由配置（2个路由） |

### 2. 页面组件（1个大屏页面）

**Index.vue - 大屏可视化页面**

#### 整体布局
```
┌─────────────────────────────────────────────────────────────┐
│                    东软环保公众监督系统 - 决策者端              │
├─────────────┬───────────────────────┬───────────────────────┤
│             │                       │                       │
│   左侧面板   │      中间主区域       │      右侧面板         │
│             │                       │                       │
│  数据卡片    │    各省AQI超标柱状图   │   12个月AQI趋势折线图  │
│  AQI分布饼图 │                       │   各省排名条形图       │
│             │                       │                       │
└─────────────┴───────────────────────┴───────────────────────┘
```

#### 左侧面板

**数据卡片（3个）**
```
1. AQI数据总量
   - 调用 /statistics/getAqiCount
   - 蓝色大字体显示

2. AQI良好数据量
   - 调用 /statistics/getAqiGoodCount
   - 绿色大字体显示

3. AQI良好率
   - 计算：良好数/总数 * 100%
   - 橙色大字体显示
```

**AQI分布饼图**
```
- 调用 /statistics/listAqiDistributeTotalStatis
- 环形饼图
- 显示各AQI等级名称和数量
```

#### 中间主区域

**各省AQI超标柱状图**
```
- 调用 /statistics/listProvinceItemTotalStatis
- X轴：省名称（旋转45度）
- Y轴：次数
- 渐变柱状图（橙色到红色）
```

#### 右侧面板

**12个月AQI趋势折线图**
```
- 调用 /statistics/listAqiTrendTotalStatis
- X轴：月份
- Y轴：超标次数
- 平滑曲线、渐变区域
```

**各省排名条形图**
```
- 调用 /statistics/listProvinceItemTotalStatis
- 取前10名
- 水平条形图
```

#### 样式设计

```css
/* 整体背景 */
background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);

/* 卡片样式 */
background: rgba(255, 255, 255, 0.05);
border: 1px solid rgba(255, 255, 255, 0.1);
```

### 3. 涉及API汇总（5个）

| API | 方法 | 用途 |
|-----|------|------|
| /statistics/getAqiCount | GET | AQI总数 |
| /statistics/getAqiGoodCount | GET | AQI良好数 |
| /statistics/listAqiDistributeTotalStatis | GET | 分布统计 |
| /statistics/listProvinceItemTotalStatis | GET | 省统计 |
| /statistics/listAqiTrendTotalStatis | GET | 趋势统计 |

---

## API接口分配总览（共21个）

| API | 队长 | 成员1 | 成员2 | 成员3 | 成员4 |
|-----|------|-------|-------|-------|-------|
| /admins/getAdminsByCode | ✅开发 | | | ✅调用 | |
| /supervisor/saveSupervisor | ✅开发 | ✅调用 | | | |
| /supervisor/getSupervisorByIdByPass | ✅开发 | ✅调用 | | | |
| /supervisor/getSupervisorById | ✅开发 | ✅调用 | | | |
| /aqi/listAqiAll | ✅开发 | ✅调用 | ✅调用 | | |
| /gridProvince/listGridProvinceAll | ✅开发 | ✅调用 | | | |
| /gridCity/listGridCityByProvinceId | ✅开发 | ✅调用 | | | |
| /gridMember/listGridMemberByProvinceId | ✅开发 | ✅调用 | | ✅调用 | |
| /gridMember/getGridMemberByCodeByPass | ✅开发 | | ✅调用 | | |
| /aqiFeedback/saveAqiFeedback | ✅开发 | ✅调用 | | | |
| /aqiFeedback/listAqiFeedbackByTelId | ✅开发 | ✅调用 | | | |
| /aqiFeedback/listAqiFeedbackPage | ✅开发 | | | ✅调用 | |
| /aqiFeedback/getAqiFeedbackById | ✅开发 | | | ✅调用 | |
| /aqiFeedback/updateAqiFeedbackAssign | ✅开发 | | | ✅调用 | |
| /aqiFeedback/listAqiFeedbackByGmId | ✅开发 | | ✅调用 | | |
| /aqiFeedback/updateAqiFeedbackState | ✅开发 | | ✅调用 | | |
| /statistics/saveStatistics | ✅开发 | | ✅调用 | | |
| /statistics/listStatisticsPage | ✅开发 | | | ✅调用 | |
| /statistics/getStatisticsById | ✅开发 | | | ✅调用 | |
| /statistics/listProvinceItemTotalStatis | ✅开发 | | | ✅调用 | ✅调用 |
| /statistics/listAqiDistributeTotalStatis | ✅开发 | | | ✅调用 | ✅调用 |
| /statistics/listAqiTrendTotalStatis | ✅开发 | | | ✅调用 | ✅调用 |
| /statistics/getAqiCount | ✅开发 | | | ✅调用 | ✅调用 |
| /statistics/getAqiGoodCount | ✅开发 | | | ✅调用 | ✅调用 |

---

## 工作量评估

| 角色 | 页面数 | 图表数 | API开发 | API调用 | 难度 |
|------|--------|--------|---------|---------|------|
| 队长 | - | - | 21 | - | ⭐⭐⭐⭐⭐ |
| 成员1 | 5 | 0 | - | 9 | ⭐⭐⭐ |
| 成员2 | 3 | 0 | - | 5 | ⭐⭐ |
| 成员3 | 10 | 4 | - | 11 | ⭐⭐⭐⭐⭐ |
| 成员4 | 1 | 4 | - | 5 | ⭐⭐⭐ |

---

## 协作规范

### 1. Git分支管理
```
main          - 主分支（队长管理）
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
```

### 3. 开发流程
1. `git pull origin main` 拉取最新代码
2. `git checkout -b dev-memberX` 创建开发分支
3. 开发完成后提交代码
4. 向 main 分支发起 Pull Request
5. 队长审核后合并

---

## 开发环境

| 工具 | 版本 |
|------|------|
| JDK | 21 |
| Maven | 3.9+ |
| MySQL | 8.0 |
| Node.js | 16+ |
| npm | 8+ |

---

## 项目启动顺序

```bash
# 1. 启动 MySQL 服务

# 2. 创建数据库
mysql -u root -p < sql/nep_db.sql

# 3. 启动后端服务
cd nep-server
mvn spring-boot:run

# 4. 启动前端
cd neps-front && npm run serve    # 成员1
cd nepg-front && npm run serve    # 成员2
cd nepm-front && npm run serve    # 成员3
cd nepv-front && npm run serve    # 成员4
```

---

## 截止时间

| 阶段 | 截止日期 | 交付物 | 负责人 |
|------|----------|--------|--------|
| 环境搭建 | [日期] | 开发环境就绪 | 全员 |
| 数据库设计 | [日期] | nep_db.sql | 队长 |
| 后端开发 | [日期] | 21个API接口 | 队长 |
| NEPS开发 | [日期] | 5个页面 | 成员1 |
| NEPG开发 | [日期] | 3个页面 | 成员2 |
| NEPM开发 | [日期] | 10个页面+4个图表 | 成员3 |
| NEPV开发 | [日期] | 1个大屏页面 | 成员4 |
| 联调测试 | [日期] | 功能测试通过 | 全员 |
| 项目答辩 | [日期] | 项目演示 | 全员 |
