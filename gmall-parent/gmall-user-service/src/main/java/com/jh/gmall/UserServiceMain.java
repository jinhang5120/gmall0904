package com.jh.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jh.gmall.mapper")
@EnableDubbo
public class UserServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceMain.class,args);
    }
}
