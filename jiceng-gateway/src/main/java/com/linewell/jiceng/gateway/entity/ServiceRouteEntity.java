package com.linewell.jiceng.gateway.entity;

import lombok.Data;

import javax.persistence.*;

/***
 *  @author wping created on 2021-01-26 10:49 
 */
@Data
@Entity
@Table(name = "jc_service_route")
public class ServiceRouteEntity extends BaseEntity {


    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "interface_name")
    private String interfaceName;

    @Column(name = "predicates")
    private String predicates;

    @Column(name = "filters")
    private String filters;

    @Column(name = "uri")
    private String uri;

    @Column(name = "path")
    private String path;

    @Column(name = "status", columnDefinition = "Bit default '1'")
    private Boolean status = true;

    @Column(name = "need_token", columnDefinition = "Bit default '1'")
    private Boolean needToken = true;



}
