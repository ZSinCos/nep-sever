package com.neusoft.nepserver.entity;

import lombok.Data;

@Data
public class AqiFeedback {
    private Integer afId;
    private String telId;
    private String province;
    private String city;
    private String afAddress;
    private Integer afAqi;
    private String afDate;
    private String afTime;
    private String afImg;
    private String afContent;
    private Integer afState;
    private String gmId;
    private String assignDate;
    private String assignTime;
    private String adminId;
}
