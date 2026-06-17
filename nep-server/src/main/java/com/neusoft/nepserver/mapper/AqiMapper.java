package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.Aqi;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AqiMapper {
    List<Aqi> listAqiAll();
}
