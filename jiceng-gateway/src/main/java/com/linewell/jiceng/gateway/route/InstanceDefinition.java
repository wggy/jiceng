package com.linewell.jiceng.gateway.route;

import lombok.Data;

import java.util.Map;

/***
 *  @author wping created on 2021-01-25 15:40 
 */
@Data
public class InstanceDefinition {
    private String instanceId;
    private String serviceId;
    private String ip;
    private int port;
    private Map<String, String> metadata;
}
