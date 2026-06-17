package com.neusoft.nepserver.entity;

import lombok.Data;

@Data
public class Supervisor {
    private String telId;
    private String supervisorName;
    private String supervisorPassword;
    private String province;
    private String city;
    private String registerDate;
}
