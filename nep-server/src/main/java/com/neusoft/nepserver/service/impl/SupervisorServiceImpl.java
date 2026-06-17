package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.entity.Supervisor;
import com.neusoft.nepserver.mapper.SupervisorMapper;
import com.neusoft.nepserver.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupervisorServiceImpl implements SupervisorService {
    
    @Autowired
    private SupervisorMapper supervisorMapper;
    
    @Override
    public Supervisor getSupervisorById(String telId) {
        return supervisorMapper.getSupervisorById(telId);
    }
    
    @Override
    public int saveSupervisor(Supervisor supervisor) {
        return supervisorMapper.saveSupervisor(supervisor);
    }
    
    @Override
    public Supervisor getSupervisorByIdByPass(String telId, String supervisorPassword) {
        return supervisorMapper.getSupervisorByIdByPass(telId, supervisorPassword);
    }
}
