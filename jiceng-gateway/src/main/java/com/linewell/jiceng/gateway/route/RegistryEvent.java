package com.linewell.jiceng.gateway.route;

import org.springframework.cloud.client.ServiceInstance;

/***
 *  @author wping created on 2021-01-22 17:00 
 */
public interface RegistryEvent {



    /**
     * 新实例注册进来时触发
     * @param ServiceInstance 实例信息
     */
    void onRegistry(ServiceInstance serviceInstance);

    /**
     * 服务下线时触发
     * @param serviceId 服务id
     */
    void onRemove(String serviceId);
}
