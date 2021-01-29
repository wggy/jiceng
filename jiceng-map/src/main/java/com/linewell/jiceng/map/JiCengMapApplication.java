package com.linewell.jiceng.map;

import com.linewell.jiceng.core.JiCengCoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * 启动入口
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class},
        scanBasePackageClasses = {JiCengMapApplication.class, JiCengCoreConfig.class})
public class JiCengMapApplication {
    public static void main(String[] args) {
        SpringApplication.run(JiCengMapApplication.class, args);
    }
}
