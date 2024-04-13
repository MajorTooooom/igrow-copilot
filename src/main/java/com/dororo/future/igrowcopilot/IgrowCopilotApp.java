package com.dororo.future.igrowcopilot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = {"com.dororo.future.igrowcopilot.mapper", "com.zhien.igrow.mapper"})
@SpringBootApplication(scanBasePackages = {"com.dororo.future.igrowcopilot", "com.zhien"})
public class IgrowCopilotApp {

    public static void main(String[] args) {
        SpringApplication.run(IgrowCopilotApp.class, args);
    }

}
