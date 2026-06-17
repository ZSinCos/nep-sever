package com.neusoft.nepserver.controller;

import com.neusoft.nepserver.dto.PageResponseDto;
import com.neusoft.nepserver.entity.AqiFeedback;
import com.neusoft.nepserver.service.AqiFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aqiFeedback")
public class AqiFeedbackController {
    
    @Autowired
    private AqiFeedbackService aqiFeedbackService;
    
    @PostMapping("/saveAqiFeedback")
    public Map<String, Object> saveAqiFeedback(@RequestBody AqiFeedback aqiFeedback) {
        Map<String, Object> result = new HashMap<>();
        int rows = aqiFeedbackService.saveAqiFeedback(aqiFeedback);
        if (rows > 0) {
            result.put("code", 200);
            result.put("message", "反馈提交成功");
        } else {
            result.put("code", 400);
            result.put("message", "反馈提交失败");
        }
        return result;
    }
    
    @GetMapping("/listAqiFeedbackByTelId")
    public Map<String, Object> listAqiFeedbackByTelId(@RequestParam String telId) {
        Map<String, Object> result = new HashMap<>();
        List<AqiFeedback> list = aqiFeedbackService.listAqiFeedbackByTelId(telId);
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
    
    @GetMapping("/listAqiFeedbackPage")
    public Map<String, Object> listAqiFeedbackPage(@RequestParam(defaultValue = "") String keyword,
                                                   @RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        PageResponseDto<AqiFeedback> page = aqiFeedbackService.listAqiFeedbackPage(keyword, pageNum, pageSize);
        result.put("code", 200);
        result.put("data", page);
        return result;
    }
    
    @GetMapping("/getAqiFeedbackById")
    public Map<String, Object> getAqiFeedbackById(@RequestParam int afId) {
        Map<String, Object> result = new HashMap<>();
        AqiFeedback aqiFeedback = aqiFeedbackService.getAqiFeedbackById(afId);
        if (aqiFeedback != null) {
            result.put("code", 200);
            result.put("data", aqiFeedback);
        } else {
            result.put("code", 400);
            result.put("message", "反馈不存在");
        }
        return result;
    }
    
    @PostMapping("/updateAqiFeedbackAssign")
    public Map<String, Object> updateAqiFeedbackAssign(@RequestBody AqiFeedback aqiFeedback) {
        Map<String, Object> result = new HashMap<>();
        int rows = aqiFeedbackService.updateAqiFeedbackAssign(aqiFeedback);
        if (rows > 0) {
            result.put("code", 200);
            result.put("message", "指派成功");
        } else {
            result.put("code", 400);
            result.put("message", "指派失败");
        }
        return result;
    }
    
    @GetMapping("/listAqiFeedbackByGmId")
    public Map<String, Object> listAqiFeedbackByGmId(@RequestParam String gmId) {
        Map<String, Object> result = new HashMap<>();
        List<AqiFeedback> list = aqiFeedbackService.listAqiFeedbackByGmId(gmId);
        result.put("code", 200);
        result.put("data", list);
        return result;
    }
    
    @PostMapping("/updateAqiFeedbackState")
    public Map<String, Object> updateAqiFeedbackState(@RequestBody AqiFeedback aqiFeedback) {
        Map<String, Object> result = new HashMap<>();
        int rows = aqiFeedbackService.updateAqiFeedbackState(aqiFeedback);
        if (rows > 0) {
            result.put("code", 200);
            result.put("message", "状态更新成功");
        } else {
            result.put("code", 400);
            result.put("message", "状态更新失败");
        }
        return result;
    }
}
