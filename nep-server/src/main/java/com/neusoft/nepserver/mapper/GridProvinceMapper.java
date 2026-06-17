package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.GridProvince;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GridProvinceMapper {
    List<GridProvince> listGridProvinceAll();
}
