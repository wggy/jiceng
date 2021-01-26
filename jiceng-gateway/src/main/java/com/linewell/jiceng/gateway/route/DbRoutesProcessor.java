package com.linewell.jiceng.gateway.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.linewell.jiceng.gateway.entity.ServiceRouteEntity;
import com.linewell.jiceng.gateway.repository.ServiceRouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 *  @author wping created on 2021-01-25 17:49 
 */
@Slf4j
@Component
public class DbRoutesProcessor implements RoutesProcessor {


    private final ServiceRouteRepository serviceRouteRepository;

    public DbRoutesProcessor(final ServiceRouteRepository serviceRouteRepository) {
        this.serviceRouteRepository = serviceRouteRepository;
    }


    @Override
    @Transactional
    public void removeAllRoutes(String serviceId) {
        serviceRouteRepository.deleteByServiceId(serviceId);
    }

    @Override
    public void saveRoutes(ServiceRouteInfo serviceRouteInfo, InstanceDefinition instance) {
        log.info("保存路由信息到数据库，instance: {}", instance);
        String serviceId = serviceRouteInfo.getServiceId();
        List<ServiceRouteEntity> serviceRoutes = serviceRouteInfo
                .getRouteDefinitionList()
                .parallelStream()
                .map(i -> toEntity(i, instance))
                .collect(Collectors.toList());

        // 删除serviceId下所有的路由
        this.removeAllRoutes(serviceId);

        if (CollectionUtils.isNotEmpty(serviceRoutes)) {
            // 批量保存
            serviceRouteRepository.saveAll(serviceRoutes);
            // 后续处理操作
//            this.initServiceBeanInitializer(serviceId);
        }
    }


    private ServiceRouteEntity toEntity(RouteDefinition routeDefinition, InstanceDefinition instance) {
        ServiceRouteEntity entity = new ServiceRouteEntity();
        entity.setServiceId(instance.getServiceId());
        entity.setInterfaceName(routeDefinition.getName());
        entity.setUri(routeDefinition.getUri());
        entity.setPath(routeDefinition.getPath());
        entity.setFilters("[]");
        entity.setPredicates("[]");
        entity.setStatus(routeDefinition.getStatus());
        entity.setNeedToken(routeDefinition.getNeedToken());
        return entity;
    }


}
