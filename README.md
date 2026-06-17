# 东软环保公众监督系统 (NEP System)

基于 Spring Boot + Vue 3 + MyBatis + MySQL 的环保公众监督系统，实现空气质量监测的全流程管理。

## 项目简介

本系统为四端架构，用于空气质量的公众监督、网格员确认、系统管理和数据可视化展示。

## 系统架构

```
┌─────────────────────────────────────────────────────────────────┐
│                        东软环保公众监督系统                        │
├─────────┬─────────┬─────────┬─────────┬─────────────────────────┤
│  NEPS   │  NEPG   │  NEPM   │  NEPV   │      nep-server         │
│ 公众端  │ 网格端  │ 管理端  │ 决策端  │     Spring Boot         │
│  Vue3   │  Vue3   │ Vue3+ECharts │ Vue3+ECharts │    + MyBatis        │
│ :8081   │ :8083   │ :8082   │ :8084   │        :8080            │
└────┬────┴────┬────┴────┬────┴────┬────┴───────────┬─────────────┘
     │         │         │         │                │
     └─────────┴─────────┴─────────┴────────────────┘
                              │
                     ┌────────┴────────┐
                     │   MySQL 8.0     │
                     │    nep_db       │
                     └─────────────────┘
```

## 功能模块

### 1. NEPS 公众监督员端 (端口: 8081)
- 用户注册与登录
- 选择监测区域（省/市/网格员）
- 预估AQI级别并提交反馈
- 查看历史反馈记录

### 2. NEPG 网格员端 (端口: 8083)
- 网格员登录
- 查看指派的反馈列表
- 确认AQI数据（PM2.5、PM10、SO2、NO2、CO、O3）

### 3. NEPM 系统管理端 (端口: 8082)
- 管理员登录
- 反馈列表管理与指派
- 确认数据查看
- 统计分析：
  - 各省AQI超标统计（柱状图）
  - AQI等级分布（饼图）
  - 12个月AQI趋势（折线图）
  - 数据总量统计

### 4. NEPV 决策者端 (端口: 8084)
- 大屏数据可视化展示
- 多维度统计图表
- 实时数据监控

## 技术栈

### 后端
| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 4.0.7 | 应用框架 |
| MyBatis | 3.5.19 | ORM框架 |
| MySQL | 8.0 | 数据库 |
| Maven | 3.9+ | 项目构建 |

### 前端
| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.x | 前端框架 |
| Vue Router | 4.x | 路由管理 |
| Vuex | 4.x | 状态管理 |
| Element Plus | 2.x | UI组件库 |
| ECharts | 5.x | 图表库 |
| Axios | 1.1.3 | HTTP客户端 |

## 项目结构

```
nep-system/
├── nep-server/                 # 后端服务
│   └── src/main/java/
│       └── com/neusoft/nepserver/
│           ├── config/         # 配置类
│           ├── controller/     # 控制器
│           ├── dto/            # 数据传输对象
│           ├── entity/         # 实体类
│           ├── mapper/         # Mapper接口
│           └── service/        # 服务层
├── neps-front/                 # 公众监督员端 (8081)
├── nepg-front/                 # 网格员端 (8083)
├── nepm-front/                 # 系统管理端 (8082)
├── nepv-front/                 # 决策者端 (8084)
├── sql/
│   └── nep_db.sql              # 数据库脚本
├── README.md
└── .gitignore
```

## 快速开始

### 环境要求
- JDK 21+
- Maven 3.9+
- MySQL 8.0+
- Node.js 16+
- npm 8+

### 1. 数据库配置

```bash
# 登录MySQL
mysql -u root -p

# 执行数据库脚本
source sql/nep_db.sql
```

### 2. 后端启动

```bash
# 进入后端目录
cd nep-server

# 修改数据库配置（application.yaml）
# spring.datasource.username: 你的MySQL用户名
# spring.datasource.password: "你的MySQL密码"

# 启动项目
mvn spring-boot:run
```

后端服务地址：http://localhost:8080/nepm/

### 3. 前端启动

```bash
# 公众监督员端
cd neps-front
npm install
npm run serve
# 访问: http://localhost:8081

# 网格员端
cd nepg-front
npm install
npm run serve
# 访问: http://localhost:8083

# 系统管理端
cd nepm-front
npm install
npm run serve
# 访问: http://localhost:8082

# 决策者端
cd nepv-front
npm install
npm run serve
# 访问: http://localhost:8084
```

## 测试账号

| 角色 | 账号 | 密码 |
|------|------|------|
| 管理员 | admin | 123456 |
| 网格员 | GM001 | 123456 |
| 公众监督员 | 需注册 | - |

## API接口

### 管理员模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /admins/getAdminsByCode | GET | 管理员登录 |

### 公众监督员模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /supervisor/saveSupervisor | POST | 注册 |
| /supervisor/getSupervisorByIdByPass | GET | 登录 |
| /supervisor/getSupervisorById | GET | 验证手机号 |

### AQI模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /aqi/listAqiAll | GET | 查询所有AQI级别 |

### 网格模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /gridProvince/listGridProvinceAll | GET | 查询省列表 |
| /gridCity/listGridCityByProvinceId | GET | 查询市列表 |
| /gridMember/listGridMemberByProvinceId | GET | 查询网格员 |
| /gridMember/getGridMemberByCodeByPass | GET | 网格员登录 |

### 反馈模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /aqiFeedback/saveAqiFeedback | POST | 提交反馈 |
| /aqiFeedback/listAqiFeedbackPage | GET | 分页查询反馈 |
| /aqiFeedback/getAqiFeedbackById | GET | 查询反馈详情 |
| /aqiFeedback/updateAqiFeedbackAssign | POST | 指派网格员 |
| /aqiFeedback/listAqiFeedbackByTelId | GET | 查询用户反馈 |
| /aqiFeedback/listAqiFeedbackByGmId | GET | 查询网格员反馈 |
| /aqiFeedback/updateAqiFeedbackState | POST | 更新反馈状态 |

### 统计模块
| 接口 | 方法 | 说明 |
|------|------|------|
| /statistics/saveStatistics | POST | 保存统计数据 |
| /statistics/listStatisticsPage | GET | 分页查询统计 |
| /statistics/getStatisticsById | GET | 查询统计详情 |
| /statistics/listProvinceItemTotalStatis | GET | 省AQI统计 |
| /statistics/listAqiDistributeTotalStatis | GET | AQI分布统计 |
| /statistics/listAqiTrendTotalStatis | GET | AQI趋势统计 |
| /statistics/getAqiCount | GET | AQI总数 |
| /statistics/getAqiGoodCount | GET | AQI良好数 |

## 数据库设计

### ER图

```
admins ─────────────────────────────────────┐
    │                                        │
    │ admin_id                               │
    │                                        ▼
    │         ┌──────────────┐    ┌──────────────────┐
    │         │ grid_province │◄───│    grid_city      │
    │         └──────┬───────┘    └────────┬─────────┘
    │                │                     │
    │                │ province_id         │ city_id
    │                │                     │
    │                ▼                     ▼
    │         ┌─────────────────────────────────┐
    │         │          grid_member            │
    │         └───────────────┬─────────────────┘
    │                         │
    │                         │ gm_id
    │                         ▼
    │    ┌──────────┐   ┌──────────────┐   ┌────────────┐
    │    │supervisor│──►│ aqi_feedback │◄──│statistics  │
    │    └──────────┘   └──────────────┘   └────────────┘
    │         │                │                  │
    │         │ tel_id         │ af_id            │ sta_id
    │         ▼                ▼                  ▼
    │    ┌─────────────────────────────────────────────┐
    └───►│                    aqi                      │
         └─────────────────────────────────────────────┘
```

### 数据表说明

| 表名 | 说明 |
|------|------|
| admins | 系统管理员表 |
| supervisor | 公众监督员表 |
| aqi | 空气质量指数级别表 |
| grid_province | 省区域表 |
| grid_city | 市区域表 |
| grid_member | 网格员表 |
| aqi_feedback | 反馈表 |
| statistics | 统计数据表 |

## 部署说明

### 打包后端
```bash
cd nep-server
mvn clean package
java -jar target/nep-server-0.0.1-SNAPSHOT.jar
```

### 打包前端
```bash
cd neps-front
npm run build
# 将dist目录部署到Nginx
```

### Nginx配置示例
```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 公众监督员端
    location /neps/ {
        alias /path/to/neps-front/dist/;
        try_files $uri $uri/ /neps/index.html;
    }

    # API代理
    location /nepm/ {
        proxy_pass http://localhost:8080/nepm/;
    }
}
```

## 常见问题

### 1. MySQL连接失败
- 检查MySQL服务是否启动
- 确认application.yaml中的数据库配置
- 密码如果是纯数字需要加引号：`password: "your_password"`

### 2. 前端依赖安装慢
```bash
# 使用淘宝镜像
npm config set registry https://registry.npmmirror.com
```

### 3. 跨域问题
后端已配置CorsConfig，允许所有来源访问。

### 4. ESLint报错
已在vue.config.js中配置`lintOnSave: false`禁用ESLint检查。

### 5. 找不到模块错误
确保已安装所有依赖：
```bash
npm install axios vue-router@4 --save
```

## 开发团队

- 开发者：[你的名字]
- 学校：[学校名称]
- 指导老师：[老师姓名]

## 许可证

本项目仅供学习交流使用。

## 更新日志

### v1.0.0 (2026-06-17)
- 完成系统基础架构
- 实现四端功能
- 完成数据可视化
