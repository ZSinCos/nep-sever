package com.neusoft.nepserver.service;

import com.neusoft.nepserver.entity.GridCity;
import java.util.List;

public interface GridCityService {
    List<GridCity> listGridCityByProvinceId(String provinceId);
}
