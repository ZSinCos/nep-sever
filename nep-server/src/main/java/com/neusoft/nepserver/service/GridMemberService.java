package com.neusoft.nepserver.service;

import com.neusoft.nepserver.entity.GridMember;
import java.util.List;

public interface GridMemberService {
    List<GridMember> listGridMemberByProvinceId(String provinceId, String cityId);
    GridMember getGridMemberByCodeByPass(String gmId, String gmPassword);
}
