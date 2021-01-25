package com.linewell.jiceng.gateway.route;

/***
 *  @author wping created on 2021-01-25 15:41 
 */
public interface ServiceListener {

    void onRemoveService(String serviceId);

    void onAddInstance(InstanceDefinition instance);
}
