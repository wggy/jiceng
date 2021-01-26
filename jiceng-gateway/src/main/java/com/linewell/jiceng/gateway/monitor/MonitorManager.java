package com.linewell.jiceng.gateway.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/***
 *  @author wping created on 2021-01-26 15:55 
 */
public class MonitorManager {

    private static Map<String, MonitorData> monitorMap = new ConcurrentHashMap<>(128);

    public Map<String, MonitorData> getMonitorData() {
        return monitorMap;
    }

    public MonitorData getMonitorInfo(String routeId, Function<String, MonitorData> createFun) {
        return monitorMap.computeIfAbsent(routeId, createFun);
    }

}
