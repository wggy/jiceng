package com.linewell.jiceng.gateway.route;

import lombok.Data;

/***
 *  @author wping created on 2021-01-26 13:15 
 */
@Data
public class ServiceDefinition {
    /**
     * 服务名称，对应spring.application.name
     */
    private String serviceId;

    public ServiceDefinition(String serviceId) {
        this.serviceId = serviceId;
    }

    public String fetchServiceIdLowerCase() {
        return serviceId.toLowerCase();
    }
}
