package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.GridMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface GridMemberMapper {
    List<GridMember> listGridMemberByProvinceId(@Param("provinceId") String provinceId, @Param("cityId") String cityId);
    GridMember getGridMemberByCodeByPass(@Param("gmId") String gmId, @Param("gmPassword") String gmPassword);
}
