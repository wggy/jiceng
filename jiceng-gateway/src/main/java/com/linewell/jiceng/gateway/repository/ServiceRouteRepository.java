package com.linewell.jiceng.gateway.repository;

import com.linewell.jiceng.gateway.entity.ServiceRouteEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/***
 *  @author wping created on 2021-01-26 11:09 
 */
public interface ServiceRouteRepository extends PagingAndSortingRepository<ServiceRouteEntity, Long> {


    void deleteByServiceId(String serviceId);

}
