package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.entity.GridMember;
import com.neusoft.nepserver.service.GridMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gridMember")
public class GridMemberController {
    
    @Autowired
    private GridMemberService gridMemberService;
    
    @GetMapping("/listGridMemberByProvinceId")
    public Map<String, Object> listGridMemberByProvinceId(@RequestParam String provinceId, @RequestParam String cityId) {
        Map<String, Object> result = new HashMap<>();
        List<GridMember> list = gridMemberService.listGridMemberByProvinceId(provinceId, cityId);
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
    
    @GetMapping("/getGridMemberByCodeByPass")
    public Map<String, Object> getGridMemberByCodeByPass(@RequestParam String gmId, @RequestParam String gmPassword) {
        Map<String, Object> result = new HashMap<>();
        GridMember gridMember = gridMemberService.getGridMemberByCodeByPass(gmId, gmPassword);
        if (gridMember != null) {
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("data", gridMember);
        } else {
            result.put("code", 400);
            result.put("message", "账号或密码错误");
        }
        return result;
    }
}
