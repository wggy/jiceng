package com.linewell.jiceng.map;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@NacosConfigurationProperties(dataId = "example", autoRefreshed = true)
public class JiCengMapApplication {
    public static void main(String[] args) {
        SpringApplication.run(JiCengMapApplication.class, args);
    }
}
