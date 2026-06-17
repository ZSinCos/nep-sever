package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.Statistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {
    int saveStatistics(Statistics statistics);
    List<Statistics> listStatisticsPage(@Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize);
    int getStatisticsCount(@Param("keyword") String keyword);
    Statistics getStatisticsById(@Param("staId") int staId);
    List<Map<String, Object>> listProvinceItemTotalStatis();
    List<Map<String, Object>> listAqiDistributeTotalStatis();
    List<Map<String, Object>> listAqiTrendTotalStatis();
    long getAqiCount();
    long getAqiGoodCount();
}
