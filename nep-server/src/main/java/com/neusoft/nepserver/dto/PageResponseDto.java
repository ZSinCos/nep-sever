package com.neusoft.nepserver.dto;

import lombok.Data;
import java.util.List;

@Data
public class PageResponseDto<T> {
    private List<T> list;
    private int total;
    private int pageNum;
    private int pageSize;
    
    public PageResponseDto(List<T> list, int total, int pageNum, int pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
