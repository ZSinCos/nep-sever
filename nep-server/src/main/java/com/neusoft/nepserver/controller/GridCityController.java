package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.entity.GridCity;
import com.neusoft.nepserver.service.GridCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gridCity")
public class GridCityController {
    
    @Autowired
    private GridCityService gridCityService;
    
    @GetMapping("/listGridCityByProvinceId")
    public Map<String, Object> listGridCityByProvinceId(@RequestParam String provinceId) {
        Map<String, Object> result = new HashMap<>();
        List<GridCity> list = gridCityService.listGridCityByProvinceId(provinceId);
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
}
