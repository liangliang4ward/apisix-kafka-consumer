package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gaoll
 * @time 2022/4/11 11:05
 **/
@SpringBootApplication
@MapperScan({"com.demo.service.mapper"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
