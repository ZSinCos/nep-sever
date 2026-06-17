package com.neusoft.nepserver.service;

import com.neusoft.nepserver.entity.Admins;

public interface AdminsService {
    Admins getAdminsByCode(String adminId, String adminPassword);
}
