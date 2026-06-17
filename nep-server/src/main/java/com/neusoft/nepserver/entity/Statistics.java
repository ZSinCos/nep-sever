package com.neusoft.nepserver.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Statistics {
    private Integer staId;
    private Integer afId;
    private String gmId;
    private Integer staAqi;
    private BigDecimal staPm25;
    private BigDecimal staPm10;
    private BigDecimal staSo2;
    private BigDecimal staNo2;
    private BigDecimal staCo;
    private BigDecimal staO3;
    private String staDate;
    private String staTime;
    private String staRemark;
}
