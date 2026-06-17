package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.entity.Aqi;
import com.neusoft.nepserver.mapper.AqiMapper;
import com.neusoft.nepserver.service.AqiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AqiServiceImpl implements AqiService {
    
    @Autowired
    private AqiMapper aqiMapper;
    
    @Override
    public List<Aqi> listAqiAll() {
        return aqiMapper.listAqiAll();
    }
}
