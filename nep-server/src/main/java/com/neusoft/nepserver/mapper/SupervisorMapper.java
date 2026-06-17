package com.neusoft.nepserver.mapper;

import com.neusoft.nepserver.entity.Supervisor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SupervisorMapper {
    Supervisor getSupervisorById(@Param("telId") String telId);
    int saveSupervisor(Supervisor supervisor);
    Supervisor getSupervisorByIdByPass(@Param("telId") String telId, @Param("supervisorPassword") String supervisorPassword);
}
