package com.linewell.jiceng.gateway.route;

import lombok.Data;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.Date;
import java.util.List;

/***
 *  @author wping created on 2021-01-25 15:52 
 */
@Data
public class ServiceRouteInfo {

    private String serviceId;

    private Date createTime = new Date();

    private Date updateTime = new Date();

    private String description;

    private List<RouteDefinition> routeDefinitionList;

    public String fetchServiceIdLowerCase() {
        return serviceId.toLowerCase();
    }
}
