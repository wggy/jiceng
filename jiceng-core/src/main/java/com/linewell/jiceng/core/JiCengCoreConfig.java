package com.linewell.jiceng.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/***
 *  @author wping created on 2021-01-29 11:50
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = JiCengCoreConfig.class)
public class JiCengCoreConfig {
}
