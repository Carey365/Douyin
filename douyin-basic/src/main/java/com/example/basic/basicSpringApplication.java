package com.example.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author chenlianghao
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.example.basic.**.mapper"})
public class basicSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(basicSpringApplication.class, args);
    }
}