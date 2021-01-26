package com.linewell.jiceng.gateway.route;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.synchronizedMap;

/***
 *
 *  路由存储管理，负责动态更新路由
 *
 *  @author wping created on 2021-01-26 13:21 
 */
public class GatewayRouteRepository implements RouteRepository<GatewayTargetRoute>, RouteLocator {

    private static final Map<String, GatewayTargetRoute> routes = synchronizedMap(new LinkedHashMap<>());
    @Autowired
    private RouteLocatorBuilder routeLocatorBuilder;

    @Autowired
    private ApplicationContext applicationContext;

    private volatile RouteLocator routeLocator;



    public void refresh() {
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
        List<RouteDefinition> routeDefinitionList = routes.values()
                .stream()
                .map(GatewayTargetRoute::getRouteDefinition)
                .collect(Collectors.toList());
        routeDefinitionList.forEach(i -> builder.route(i.getUri(), r -> r.path(i.getPath()).uri(i.getUri())));
        this.routeLocator = builder.build();
        // 触发
        applicationContext.publishEvent(new RefreshRoutesEvent(new Object()));
    }

    @Override
    public GatewayTargetRoute get(String id) {
        if (id == null) {
            return null;
        }
        return routes.get(id);
    }

    @Override
    public Collection<GatewayTargetRoute> getAll() {
        return routes.values();
    }

    @Override
    public String add(GatewayTargetRoute targetRoute) {
        RouteDefinition routeDefinition = targetRoute.getRouteDefinition();
        routes.put(routeDefinition.getUri(), targetRoute);
        return "success";
    }

    @Override
    public void update(GatewayTargetRoute targetRoute) {
        RouteDefinition baseRouteDefinition = targetRoute.getRouteDefinition();
        routes.put(baseRouteDefinition.getUri(), targetRoute);
    }

    @Override
    public void delete(String id) {
        routes.remove(id);
    }

    @Override
    public void deleteAll(String serviceId) {
        List<String> idList = routes.values().stream()
                .filter(i -> StringUtils.equalsIgnoreCase(serviceId, i.getServiceDefinition().getServiceId()))
                .map(i -> i.getRouteDefinition().getUri())
                .collect(Collectors.toList());

        idList.forEach(this::delete);
    }

    @Override
    public Flux<Route> getRoutes() {
        if (routeLocator == null) {
            return Flux.empty();
        }
        return routeLocator.getRoutes();
    }

}
