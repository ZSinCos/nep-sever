-- =============================================
-- 东软环保公众监督系统 数据库创建脚本
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS nep_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE nep_db;

-- =============================================
-- 1. 系统管理员表 (admins)
-- =============================================
DROP TABLE IF EXISTS statistics;
DROP TABLE IF EXISTS aqi_feedback;
DROP TABLE IF EXISTS grid_member;
DROP TABLE IF EXISTS grid_city;
DROP TABLE IF EXISTS grid_province;
DROP TABLE IF EXISTS aqi;
DROP TABLE IF EXISTS supervisor;
DROP TABLE IF EXISTS admins;

CREATE TABLE admins (
    admin_id VARCHAR(20) NOT NULL COMMENT '管理员编码',
    admin_name VARCHAR(100) COMMENT '管理员姓名',
    admin_password VARCHAR(50) NOT NULL COMMENT '管理员密码',
    PRIMARY KEY (admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理员表';

-- =============================================
-- 2. 公众监督员表 (supervisor)
-- =============================================
CREATE TABLE supervisor (
    tel_id VARCHAR(20) NOT NULL COMMENT '手机号码(主键)',
    supervisor_name VARCHAR(50) COMMENT '监督员姓名',
    supervisor_password VARCHAR(50) NOT NULL COMMENT '监督员密码',
    province VARCHAR(50) COMMENT '省',
    city VARCHAR(50) COMMENT '市',
    register_date VARCHAR(20) COMMENT '注册日期',
    PRIMARY KEY (tel_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公众监督员表';

-- =============================================
-- 3. 空气质量指数级别表 (aqi)
-- =============================================
CREATE TABLE aqi (
    aqi_id INT NOT NULL COMMENT 'AQI级别编码(主键)',
    aqi_name VARCHAR(50) COMMENT 'AQI级别名称',
    aqi_min INT COMMENT 'AQI最小值',
    aqi_max INT COMMENT 'AQI最大值',
    aqi_color VARCHAR(20) COMMENT 'AQI颜色',
    aqi_description VARCHAR(200) COMMENT 'AQI描述',
    PRIMARY KEY (aqi_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='空气质量指数级别表';

-- =============================================
-- 4. 系统网格覆盖省区域表 (grid_province)
-- =============================================
CREATE TABLE grid_province (
    province_id VARCHAR(20) NOT NULL COMMENT '省编码(主键)',
    province_name VARCHAR(50) COMMENT '省名称',
    PRIMARY KEY (province_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统网格覆盖省区域表';

-- =============================================
-- 5. 系统网格覆盖市区域表 (grid_city)
-- =============================================
CREATE TABLE grid_city (
    city_id VARCHAR(20) NOT NULL COMMENT '市编码(主键)',
    city_name VARCHAR(50) COMMENT '市名称',
    province_id VARCHAR(20) NOT NULL COMMENT '所属省编码(外键)',
    PRIMARY KEY (city_id),
    FOREIGN KEY (province_id) REFERENCES grid_province(province_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统网格覆盖市区域表';

-- =============================================
-- 6. 空气质量监测网格员表 (grid_member)
-- =============================================
CREATE TABLE grid_member (
    gm_id VARCHAR(20) NOT NULL COMMENT '网格员编码(主键)',
    gm_name VARCHAR(50) COMMENT '网格员姓名',
    gm_password VARCHAR(50) NOT NULL COMMENT '网格员密码',
    gm_tel VARCHAR(20) COMMENT '网格员电话',
    province_id VARCHAR(20) COMMENT '所属省编码',
    city_id VARCHAR(20) COMMENT '所属市编码',
    gm_state INT COMMENT '状态(0:可工作 1:不可工作)',
    PRIMARY KEY (gm_id),
    FOREIGN KEY (province_id) REFERENCES grid_province(province_id),
    FOREIGN KEY (city_id) REFERENCES grid_city(city_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='空气质量监测网格员表';

-- =============================================
-- 7. 空气质量公众监督反馈表 (aqi_feedback)
-- =============================================
CREATE TABLE aqi_feedback (
    af_id INT NOT NULL AUTO_INCREMENT COMMENT '反馈编码(主键)',
    tel_id VARCHAR(20) NOT NULL COMMENT '监督员手机号(外键)',
    province VARCHAR(50) COMMENT '省',
    city VARCHAR(50) COMMENT '市',
    af_address VARCHAR(200) COMMENT '详细地址',
    af_aqi INT COMMENT '预估AQI级别',
    af_date VARCHAR(20) COMMENT '反馈日期',
    af_time VARCHAR(20) COMMENT '反馈时间',
    af_img VARCHAR(200) COMMENT '反馈图片',
    af_content VARCHAR(500) COMMENT '反馈内容',
    af_state INT COMMENT '状态(0:待指派 1:已指派 2:已确认)',
    gm_id VARCHAR(20) COMMENT '网格员编码',
    assign_date VARCHAR(20) COMMENT '指派日期',
    assign_time VARCHAR(20) COMMENT '指派时间',
    admin_id VARCHAR(20) COMMENT '指派管理员编码',
    PRIMARY KEY (af_id),
    FOREIGN KEY (tel_id) REFERENCES supervisor(tel_id),
    FOREIGN KEY (gm_id) REFERENCES grid_member(gm_id),
    FOREIGN KEY (admin_id) REFERENCES admins(admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='空气质量公众监督反馈表';

-- =============================================
-- 8. 空气质量监测数据统计表 (statistics)
-- =============================================
CREATE TABLE statistics (
    sta_id INT NOT NULL AUTO_INCREMENT COMMENT '统计编码(主键)',
    af_id INT NOT NULL COMMENT '反馈编码(外键)',
    gm_id VARCHAR(20) NOT NULL COMMENT '网格员编码(外键)',
    sta_aqi INT COMMENT '确认AQI级别',
    sta_pm25 DECIMAL(10,2) COMMENT 'PM2.5值',
    sta_pm10 DECIMAL(10,2) COMMENT 'PM10值',
    sta_so2 DECIMAL(10,2) COMMENT 'SO2值',
    sta_no2 DECIMAL(10,2) COMMENT 'NO2值',
    sta_co DECIMAL(10,2) COMMENT 'CO值',
    sta_o3 DECIMAL(10,2) COMMENT 'O3值',
    sta_date VARCHAR(20) COMMENT '确认日期',
    sta_time VARCHAR(20) COMMENT '确认时间',
    sta_remark VARCHAR(500) COMMENT '备注',
    PRIMARY KEY (sta_id),
    FOREIGN KEY (af_id) REFERENCES aqi_feedback(af_id),
    FOREIGN KEY (gm_id) REFERENCES grid_member(gm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='空气质量监测数据统计表';
