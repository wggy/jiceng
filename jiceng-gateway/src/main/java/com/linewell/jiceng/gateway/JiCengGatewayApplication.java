package com.linewell.jiceng.gateway;

import com.linewell.jiceng.gateway.route.NacosRegistryListener;
import com.linewell.jiceng.gateway.route.RegistryListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/***
 *  @author wping created on 2021-01-21 13:49 
 */
@PropertySource("classpath:db.properties")
@EnableDiscoveryClient
@SpringBootApplication
public class JiCengGatewayApplication {

    /**
     * 微服务路由加载
     */
    @Bean
    RegistryListener registryListenerNacos() {
        return new NacosRegistryListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(JiCengGatewayApplication.class, args);
    }
}
