package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.dto.PageResponseDto;
import com.neusoft.nepserver.entity.Statistics;
import com.neusoft.nepserver.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    @PostMapping("/saveStatistics")
    public Map<String, Object> saveStatistics(@RequestBody Statistics statistics) {
        Map<String, Object> result = new HashMap<>();
        int rows = statisticsService.saveStatistics(statistics);
        if (rows > 0) {
            result.put("code", 200);
            result.put("message", "保存成功");
        } else {
            result.put("code", 400);
            result.put("message", "保存失败");
        }
        return result;
    }
    
    @GetMapping("/listStatisticsPage")
    public Map<String, Object> listStatisticsPage(@RequestParam(defaultValue = "") String keyword,
                                                  @RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        PageResponseDto<Statistics> page = statisticsService.listStatisticsPage(keyword, pageNum, pageSize);
        result.put("code", 200);
        result.put("data", page);
        return result;
    }
    
    @GetMapping("/getStatisticsById")
    public Map<String, Object> getStatisticsById(@RequestParam int staId) {
        Map<String, Object> result = new HashMap<>();
        Statistics statistics = statisticsService.getStatisticsById(staId);
        if (statistics != null) {
            result.put("code", 200);
            result.put("data", statistics);
        } else {
            result.put("code", 400);
            result.put("message", "数据不存在");
        }
        return result;
    }
    
    @GetMapping("/listProvinceItemTotalStatis")
    public Map<String, Object> listProvinceItemTotalStatis() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = statisticsService.listProvinceItemTotalStatis();
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
    
    @GetMapping("/listAqiDistributeTotalStatis")
    public Map<String, Object> listAqiDistributeTotalStatis() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = statisticsService.listAqiDistributeTotalStatis();
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
    
    @GetMapping("/listAqiTrendTotalStatis")
    public Map<String, Object> listAqiTrendTotalStatis() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = statisticsService.listAqiTrendTotalStatis();
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
    
    @GetMapping("/getAqiCount")
    public Map<String, Object> getAqiCount() {
        Map<String, Object> result = new HashMap<>();
        long count = statisticsService.getAqiCount();
        result.put("code", 200);
        result.put("data", count);
        return result;
    }
    
    @GetMapping("/getAqiGoodCount")
    public Map<String, Object> getAqiGoodCount() {
        Map<String, Object> result = new HashMap<>();
        long count = statisticsService.getAqiGoodCount();
        result.put("code", 200);
        result.put("data", count);
        return result;
    }
}
