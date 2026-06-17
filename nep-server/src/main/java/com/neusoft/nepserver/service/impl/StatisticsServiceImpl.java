package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.dto.PageResponseDto;
import com.neusoft.nepserver.entity.Statistics;
import com.neusoft.nepserver.mapper.StatisticsMapper;
import com.neusoft.nepserver.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    
    @Autowired
    private StatisticsMapper statisticsMapper;
    
    @Override
    public int saveStatistics(Statistics statistics) {
        return statisticsMapper.saveStatistics(statistics);
    }
    
    @Override
    public PageResponseDto<Statistics> listStatisticsPage(String keyword, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Statistics> list = statisticsMapper.listStatisticsPage(keyword, offset, pageSize);
        int total = statisticsMapper.getStatisticsCount(keyword);
        return new PageResponseDto<>(list, total, pageNum, pageSize);
    }
    
    @Override
    public Statistics getStatisticsById(int staId) {
        return statisticsMapper.getStatisticsById(staId);
    }
    
    @Override
    public List<Map<String, Object>> listProvinceItemTotalStatis() {
        return statisticsMapper.listProvinceItemTotalStatis();
    }
    
    @Override
    public List<Map<String, Object>> listAqiDistributeTotalStatis() {
        return statisticsMapper.listAqiDistributeTotalStatis();
    }
    
    @Override
    public List<Map<String, Object>> listAqiTrendTotalStatis() {
        return statisticsMapper.listAqiTrendTotalStatis();
    }
    
    @Override
    public long getAqiCount() {
        return statisticsMapper.getAqiCount();
    }
    
    @Override
    public long getAqiGoodCount() {
        return statisticsMapper.getAqiGoodCount();
    }
}
