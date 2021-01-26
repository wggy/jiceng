package com.linewell.jiceng.gateway.route;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/***
 *  @author wping created on 2021-01-22 10:19 
 */
public abstract class BaseRegistryListener implements RegistryListener {
    private final Map<String, Long> updateTimeMap = Maps.newConcurrentMap();
    private static final int FIVE_SECONDS = 1000 * 5;

    @Autowired
    private ServiceListener serviceListener;

    public synchronized void pullRoutes(InstanceDefinition instance) {
        // serviceId统一小写
        instance.setServiceId(instance.getServiceId().toLowerCase());
        serviceListener.onAddInstance(instance);
    }

    protected void doRemove(Set<String> deletedServices) {
        if (deletedServices == null) {
            return;
        }
        deletedServices.forEach(this::removeRoutes);
    }

    /**
     * 移除路由信息
     *
     * @param serviceId serviceId
     */
    public synchronized void removeRoutes(String serviceId) {
        serviceListener.onRemoveService(serviceId.toLowerCase());
    }

    protected boolean canOperator(NacosServiceHolder serviceHolder) {
        String serviceId = serviceHolder.getServiceId();
        // 被排除的服务，不能操作
//        if (isExcludeService(serviceId)) {
//            return false;
//        }
        // nacos会不停的触发事件，这里做了一层拦截
        // 同一个serviceId5秒内允许访问一次
        Long lastUpdateTime = updateTimeMap.getOrDefault(serviceId, 0L);
        long now = System.currentTimeMillis();
        boolean can = now - lastUpdateTime > FIVE_SECONDS;
        if (can) {
            updateTimeMap.put(serviceId, now);
        }
        return can;
    }



}
