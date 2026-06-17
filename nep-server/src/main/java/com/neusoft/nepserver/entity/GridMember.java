package com.neusoft.nepserver.entity;

import lombok.Data;

@Data
public class GridMember {
    private String gmId;
    private String gmName;
    private String gmPassword;
    private String gmTel;
    private String provinceId;
    private String cityId;
    private Integer gmState;
}
