package com.linewell.jiceng.map.controller;

import com.linewell.jiceng.core.swagger.SwaggerSupport;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/***
 *  @author wping created on 2021-01-29 17:15
 */
@Configuration
@EnableSwagger2
public class Swagger2Config extends SwaggerSupport {

    @Override
    protected String getDocTitle() {
        return "图上作战APIs";
    }

    @Override
    protected boolean swaggerAccessProtected() {
        return false;
    }
}
