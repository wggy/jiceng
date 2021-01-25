package com.linewell.jiceng.gateway.route;

import com.sun.xml.internal.ws.api.server.ServiceDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

/***
 *  @author wping created on 2021-01-25 15:57 
 */
public class GatewayTargetRoute implements  TargetRoute {

    private ServiceDefinition serviceDefinition;
    private RouteDefinition routeDefinition;

    public GatewayTargetRoute(ServiceDefinition serviceDefinition, RouteDefinition routeDefinition) {
        this.serviceDefinition = serviceDefinition;
        this.routeDefinition = routeDefinition;
    }


    @Override
    public ServiceDefinition getServiceDefinition() {
        return serviceDefinition;
    }

    @Override
    public RouteDefinition getRouteDefinition() {
        return routeDefinition;
    }
}
