package com.linewell.jiceng.gateway.route;

import com.sun.xml.internal.ws.api.server.ServiceDefinition;
import lombok.Data;
import org.springframework.cloud.gateway.route.RouteDefinition;

/***
 *  @author wping created on 2021-01-25 15:56 
 */

public interface TargetRoute {

    /**
     * 返回服务信息
     *
     * @return 返回服务信息
     */
    ServiceDefinition getServiceDefinition();

    /**
     * 返回微服务路由对象
     *
     * @return 返回微服务路由对象
     */
    RouteDefinition getRouteDefinition();
}
