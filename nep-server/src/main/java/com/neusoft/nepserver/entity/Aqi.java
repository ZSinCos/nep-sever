package com.neusoft.nepserver.entity;

import lombok.Data;

@Data
public class Aqi {
    private Integer aqiId;
    private String aqiName;
    private Integer aqiMin;
    private Integer aqiMax;
    private String aqiColor;
    private String aqiDescription;
}
