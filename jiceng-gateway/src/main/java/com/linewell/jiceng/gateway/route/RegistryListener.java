package com.linewell.jiceng.gateway.route;

import org.springframework.context.ApplicationEvent;

/***
 *  @author wping created on 2021-01-22 10:18
 *
 *
 */
public interface RegistryListener {

    void onEvent(ApplicationEvent applicationEvent);
}
