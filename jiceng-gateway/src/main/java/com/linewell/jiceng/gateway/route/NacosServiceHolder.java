package com.linewell.jiceng.gateway.route;

import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/***
 *  @author wping created on 2021-01-22 16:43 
 */
@Setter
@Getter
public class NacosServiceHolder {

    private String serviceId;
    private Long lastUpdatedTimestamp;

    private final Instance instance;


    public NacosServiceHolder(String serviceId, Long lastUpdatedTimestamp, Instance instance) {
        this.serviceId = serviceId;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
        this.instance = instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NacosServiceHolder that = (NacosServiceHolder) o;
        return lastUpdatedTimestamp.equals(that.lastUpdatedTimestamp) &&
                serviceId.equals(that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, lastUpdatedTimestamp);
    }

}
