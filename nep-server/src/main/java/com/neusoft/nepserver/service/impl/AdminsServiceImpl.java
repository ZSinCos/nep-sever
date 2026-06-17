package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.entity.Admins;
import com.neusoft.nepserver.mapper.AdminsMapper;
import com.neusoft.nepserver.service.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminsServiceImpl implements AdminsService {
    
    @Autowired
    private AdminsMapper adminsMapper;
    
    @Override
    public Admins getAdminsByCode(String adminId, String adminPassword) {
        return adminsMapper.getAdminsByCode(adminId, adminPassword);
    }
}
