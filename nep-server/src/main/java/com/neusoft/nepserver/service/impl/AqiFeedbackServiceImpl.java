package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.dto.PageResponseDto;
import com.neusoft.nepserver.entity.AqiFeedback;
import com.neusoft.nepserver.mapper.AqiFeedbackMapper;
import com.neusoft.nepserver.service.AqiFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AqiFeedbackServiceImpl implements AqiFeedbackService {
    
    @Autowired
    private AqiFeedbackMapper aqiFeedbackMapper;
    
    @Override
    public int saveAqiFeedback(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.saveAqiFeedback(aqiFeedback);
    }
    
    @Override
    public List<AqiFeedback> listAqiFeedbackByTelId(String telId) {
        return aqiFeedbackMapper.listAqiFeedbackByTelId(telId);
    }
    
    @Override
    public PageResponseDto<AqiFeedback> listAqiFeedbackPage(String keyword, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<AqiFeedback> list = aqiFeedbackMapper.listAqiFeedbackPage(keyword, offset, pageSize);
        int total = aqiFeedbackMapper.getAqiFeedbackCount(keyword);
        return new PageResponseDto<>(list, total, pageNum, pageSize);
    }
    
    @Override
    public AqiFeedback getAqiFeedbackById(int afId) {
        return aqiFeedbackMapper.getAqiFeedbackById(afId);
    }
    
    @Override
    public int updateAqiFeedbackAssign(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.updateAqiFeedbackAssign(aqiFeedback);
    }
    
    @Override
    public List<AqiFeedback> listAqiFeedbackByGmId(String gmId) {
        return aqiFeedbackMapper.listAqiFeedbackByGmId(gmId);
    }
    
    @Override
    public int updateAqiFeedbackState(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.updateAqiFeedbackState(aqiFeedback);
    }
}
