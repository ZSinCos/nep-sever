package com.neusoft.nepserver.service;

import com.neusoft.nepserver.entity.Supervisor;

public interface SupervisorService {
    Supervisor getSupervisorById(String telId);
    int saveSupervisor(Supervisor supervisor);
    Supervisor getSupervisorByIdByPass(String telId, String supervisorPassword);
}
