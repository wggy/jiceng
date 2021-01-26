package com.linewell.jiceng.gateway.route;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/***
 *  @author wping created on 2021-01-26 11:48 
 */
@Data
public class RouteDefinition {

    /**
     * 接口名
     */
    private String name;

    /**
     * 路由规则转发的目标uri
     */
    private String uri;

    /**
     * uri后面跟的path
     */
    private String path;


    /**
     * 状态，false 禁用， true启动
     */
    private Boolean status = true;


    /**
     * 是否需要token
     */
    private Boolean needToken = true;
}
