package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.entity.Supervisor;
import com.neusoft.nepserver.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {
    
    @Autowired
    private SupervisorService supervisorService;
    
    @GetMapping("/getSupervisorById")
    public Map<String, Object> getSupervisorById(@RequestParam String telId) {
        Map<String, Object> result = new HashMap<>();
        Supervisor supervisor = supervisorService.getSupervisorById(telId);
        if (supervisor != null) {
            result.put("code", 200);
            result.put("data", supervisor);
        } else {
            result.put("code", 400);
            result.put("message", "用户不存在");
        }
        return result;
    }
    
    @PostMapping("/saveSupervisor")
    public Map<String, Object> saveSupervisor(@RequestBody Supervisor supervisor) {
        Map<String, Object> result = new HashMap<>();
        int rows = supervisorService.saveSupervisor(supervisor);
        if (rows > 0) {
            result.put("code", 200);
            result.put("message", "注册成功");
        } else {
            result.put("code", 400);
            result.put("message", "注册失败");
        }
        return result;
    }
    
    @GetMapping("/getSupervisorByIdByPass")
    public Map<String, Object> getSupervisorByIdByPass(@RequestParam String telId, @RequestParam String supervisorPassword) {
        Map<String, Object> result = new HashMap<>();
        Supervisor supervisor = supervisorService.getSupervisorByIdByPass(telId, supervisorPassword);
        if (supervisor != null) {
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("data", supervisor);
        } else {
            result.put("code", 400);
            result.put("message", "手机号或密码错误");
        }
        return result;
    }
}
