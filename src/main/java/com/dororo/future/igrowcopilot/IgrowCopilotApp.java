package com.dororo.future.igrowcopilot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dororo.future.igrowcopilot.util.SqliteHelper;

@EnableCaching
@EnableScheduling
@MapperScan(basePackages = {"com.dororo.future.igrowcopilot.mapper", "com.zhien.igrow.mapper"})
@SpringBootApplication(scanBasePackages = {"com.dororo.future.igrowcopilot", "com.zhien"})
public class IgrowCopilotApp {

    public static void main(String[] args) {

        SqliteHelper.init();

        SpringApplication.run(IgrowCopilotApp.class, args);
    }

}
