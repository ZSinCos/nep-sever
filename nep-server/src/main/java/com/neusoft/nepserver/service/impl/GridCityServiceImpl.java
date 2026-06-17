package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.entity.GridCity;
import com.neusoft.nepserver.mapper.GridCityMapper;
import com.neusoft.nepserver.service.GridCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GridCityServiceImpl implements GridCityService {
    
    @Autowired
    private GridCityMapper gridCityMapper;
    
    @Override
    public List<GridCity> listGridCityByProvinceId(String provinceId) {
        return gridCityMapper.listGridCityByProvinceId(provinceId);
    }
}
