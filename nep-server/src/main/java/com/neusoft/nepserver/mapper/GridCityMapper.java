package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.GridCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface GridCityMapper {
    List<GridCity> listGridCityByProvinceId(@Param("provinceId") String provinceId);
}
