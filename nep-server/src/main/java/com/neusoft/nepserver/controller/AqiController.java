package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.entity.Aqi;
import com.neusoft.nepserver.service.AqiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aqi")
public class AqiController {
    
    @Autowired
    private AqiService aqiService;
    
    @GetMapping("/listAqiAll")
    public Map<String, Object> listAqiAll() {
        Map<String, Object> result = new HashMap<>();
        List<Aqi> list = aqiService.listAqiAll();
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
}
