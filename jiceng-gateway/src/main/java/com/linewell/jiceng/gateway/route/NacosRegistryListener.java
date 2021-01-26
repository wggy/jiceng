package com.linewell.jiceng.gateway.route;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.linewell.jiceng.gateway.util.JiCengConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/***
 *  @author wping created on 2021-01-22 16:40 
 */
@Slf4j
public class NacosRegistryListener extends BaseRegistryListener {

    private static final int PAGE_NO = 1;

    private volatile Set<NacosServiceHolder> cacheServices = Sets.newConcurrentHashSet();

    @Value("${nacos.discovery.group:${spring.cloud.nacos.discovery.group:DEFAULT_GROUP}}")
    private String nacosGroup;


    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;



    @Override
    public void onEvent(ApplicationEvent applicationEvent) {
        List<NacosServiceHolder> serviceList = this.getServices();
        Set<NacosServiceHolder> newServices = Sets.newConcurrentHashSet(serviceList);

        // 删除缓存服务，剩下就是新增服务
        newServices.removeAll(cacheServices);

        newServices.forEach(this::toInstanceDefinition);


        // 如果有服务下线
        Set<String> removedServiceIdList = getRemovedServiceId(serviceList);
        // 移除
        this.doRemove(removedServiceIdList);

        cacheServices = new HashSet<>(serviceList);
    }


    private List<NacosServiceHolder> getServices() {
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());

        ListView<String> servicesOfServer = null;
        try {
            servicesOfServer = namingService.getServicesOfServer(PAGE_NO, Integer.MAX_VALUE, nacosGroup);
        } catch (NacosException e) {
            e.printStackTrace();
        }

        if (servicesOfServer == null || CollectionUtils.isEmpty(servicesOfServer.getData())) {
            return Collections.emptyList();
        }

        return servicesOfServer.getData()
                .stream()
                .map(serviceName -> {
                    List<Instance> allInstances;
                    try {
                        allInstances = namingService.getAllInstances(serviceName, nacosGroup);
                    } catch (NacosException e) {
                        log.error("namingService.getAllInstances(serviceName), serviceName：{}", serviceName, e);
                        return null;
                    }
                    if (CollectionUtils.isEmpty(allInstances)) {
                        return null;
                    }

                    return allInstances.stream()
                            .filter(Instance::isHealthy)
                            .map(instance -> {
                                String startupTime = Optional.of(instance.getMetadata()
                                        .get(JiCengConstants.METADATA_KEY_TIME_STARTUP)).orElse("0");
                                long time = NumberUtils.toLong(startupTime, 0);
                                return new NacosServiceHolder(serviceName, time, instance);
                            })
                            .filter(Objects::nonNull)
                            .max(Comparator.comparing(NacosServiceHolder::getLastUpdatedTimestamp))
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .filter(this::canOperator)
                .collect(Collectors.toList());

    }


    /**
     * 获取已经下线的serviceId
     *
     * @param serviceList 最新的serviceId集合
     * @return 返回已下线的serviceId
     */
    private Set<String> getRemovedServiceId(List<NacosServiceHolder> serviceList) {
        Set<String> cache = cacheServices.stream()
                .map(NacosServiceHolder::getServiceId)
                .collect(Collectors.toSet());

        Set<String> newList = serviceList.stream()
                .map(NacosServiceHolder::getServiceId)
                .collect(Collectors.toSet());

        cache.removeAll(newList);
        return cache;
    }


    private InstanceDefinition toInstanceDefinition(NacosServiceHolder holder) {
        Instance instance = holder.getInstance();
        InstanceDefinition instanceDefinition = new InstanceDefinition();
        instanceDefinition.setInstanceId(instance.getInstanceId());
        instanceDefinition.setServiceId(holder.getServiceId());
        instanceDefinition.setIp(instance.getIp());
        instanceDefinition.setPort(instance.getPort());
        instanceDefinition.setMetadata(instance.getMetadata());

        pullRoutes(instanceDefinition);

        return instanceDefinition;
    }


}
