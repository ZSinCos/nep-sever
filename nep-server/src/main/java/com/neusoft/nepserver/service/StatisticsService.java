package com.neusoft.nepserver.service;

import com.neusoft.nepserver.dto.PageResponseDto;
import com.neusoft.nepserver.entity.Statistics;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    int saveStatistics(Statistics statistics);
    PageResponseDto<Statistics> listStatisticsPage(String keyword, int pageNum, int pageSize);
    Statistics getStatisticsById(int staId);
    List<Map<String, Object>> listProvinceItemTotalStatis();
    List<Map<String, Object>> listAqiDistributeTotalStatis();
    List<Map<String, Object>> listAqiTrendTotalStatis();
    long getAqiCount();
    long getAqiGoodCount();
}
