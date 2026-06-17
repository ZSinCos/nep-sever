package com.neusoft.nepserver.dto;

import lombok.Data;

@Data
public class PageRequestDto {
    private String keyword;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
