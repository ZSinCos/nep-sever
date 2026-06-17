package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.entity.GridProvince;
import com.neusoft.nepserver.service.GridProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gridProvince")
public class GridProvinceController {
    
    @Autowired
    private GridProvinceService gridProvinceService;
    
    @GetMapping("/listGridProvinceAll")
    public Map<String, Object> listGridProvinceAll() {
        Map<String, Object> result = new HashMap<>();
        List<GridProvince> list = gridProvinceService.listGridProvinceAll();
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
}
