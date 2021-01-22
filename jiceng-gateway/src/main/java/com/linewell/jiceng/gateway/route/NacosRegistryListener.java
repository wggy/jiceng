package com.linewell.jiceng.gateway.route;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/***
 *  @author wping created on 2021-01-22 16:40 
 */
@Slf4j
public class NacosRegistryListener extends BaseRegistryListener {

    private static final int pageNo = 1;

    private volatile Set<NacosServiceHolder> cacheServices = Sets.newConcurrentHashSet();

    @Value("${nacos.discovery.group:${spring.cloud.nacos.discovery.group:DEFAULT_GROUP}}")
    private String nacosGroup;


    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired(required = false)
    private List<RegistryEvent> registryEventList;


    @Override
    public void onEvent(ApplicationEvent applicationEvent) {

    }


    private List<NacosServiceHolder> getServices() {
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());

        ListView<String> servicesOfServer = null;
        try {
            servicesOfServer = namingService.getServicesOfServer(pageNo, Integer.MAX_VALUE, nacosGroup);
        } catch (NacosException e) {
            e.printStackTrace();
        }

        if (servicesOfServer == null || CollectionUtils.isEmpty(servicesOfServer.getData())) {
            return Collections.emptyList();
        }

//        return servicesOfServer.getData()
//                .stream()
//                .map(i -> {
//
//                }).;

        return null;
    }
}
