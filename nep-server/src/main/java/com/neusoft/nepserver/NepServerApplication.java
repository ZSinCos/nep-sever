package com.neusoft.nepserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.neusoft.nepserver.mapper")
public class NepServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NepServerApplication.class, args);
    }

}
