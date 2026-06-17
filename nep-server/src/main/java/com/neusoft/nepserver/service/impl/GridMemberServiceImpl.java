package com.neusoft.nepserver.service.impl;

import com.neusoft.nepserver.entity.GridMember;
import com.neusoft.nepserver.mapper.GridMemberMapper;
import com.neusoft.nepserver.service.GridMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GridMemberServiceImpl implements GridMemberService {
    
    @Autowired
    private GridMemberMapper gridMemberMapper;
    
    @Override
    public List<GridMember> listGridMemberByProvinceId(String provinceId, String cityId) {
        return gridMemberMapper.listGridMemberByProvinceId(provinceId, cityId);
    }
    
    @Override
    public GridMember getGridMemberByCodeByPass(String gmId, String gmPassword) {
        return gridMemberMapper.getGridMemberByCodeByPass(gmId, gmPassword);
    }
}
