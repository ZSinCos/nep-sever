package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.Admins;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminsMapper {
    Admins getAdminsByCode(@Param("adminId") String adminId, @Param("adminPassword") String adminPassword);
}
