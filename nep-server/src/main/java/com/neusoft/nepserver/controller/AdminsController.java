package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.entity.Admins;
import com.neusoft.nepserver.service.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class AdminsController {
    
    @Autowired
    private AdminsService adminsService;
    
    @GetMapping("/getAdminsByCode")
    public Map<String, Object> getAdminsByCode(@RequestParam String adminId, @RequestParam String adminPassword) {
        Map<String, Object> result = new HashMap<>();
        Admins admins = adminsService.getAdminsByCode(adminId, adminPassword);
        if (admins != null) {
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("data", admins);
        } else {
            result.put("code", 400);
            result.put("message", "账号或密码错误");
        }
        return result;
    }
}
