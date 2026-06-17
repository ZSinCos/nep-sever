package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.entity.GridProvince;
import com.neusoft.nepserver.mapper.GridProvinceMapper;
import com.neusoft.nepserver.service.GridProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GridProvinceServiceImpl implements GridProvinceService {
    
    @Autowired
    private GridProvinceMapper gridProvinceMapper;
    
    @Override
    public List<GridProvince> listGridProvinceAll() {
        return gridProvinceMapper.listGridProvinceAll();
    }
}
