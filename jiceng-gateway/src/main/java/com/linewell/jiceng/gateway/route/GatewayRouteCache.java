package com.linewell.jiceng.gateway.route;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/***
 *  @author wping created on 2021-01-25 15:55 
 */
public class GatewayRouteCache implements RouteLoader {

    /**
     * KEY:serviceId, value: md5
     */
    private Map<String, String> serviceIdMd5Map = Maps.newConcurrentMap();

    private RouteRepository<GatewayTargetRoute> routeRepository;

    public GatewayRouteCache(RouteRepository<GatewayTargetRoute> routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public void load(ServiceRouteInfo serviceRouteInfo, Consumer<Object> callback) {

    }

    @Override
    public void remove(String serviceId) {

    }
}
