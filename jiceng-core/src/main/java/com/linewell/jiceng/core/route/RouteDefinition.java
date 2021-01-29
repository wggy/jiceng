package com.linewell.jiceng.core.route;

import lombok.Data;

import java.util.List;

/***
 *  @author wping created on 2021-01-29 13:55
 */
@Data
public class RouteDefinition {
    /**
     * 路由的Id（接口名+版本号），确保此id全局唯一
     */
    private String id;

    /**
     * 接口名
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 路由规则转发的目标uri
     */
    private String uri;

    /**
     * uri后面跟的path
     */
    private String path;

    /**
     * 路由执行的顺序
     */
    private int order = 0;


    /**
     * 状态，0：待审核，1：启用，2：禁用
     */
    private int status = 1;


    /**
     * 是否需要token
     */
    private int needToken;

    /**
     * 是否是兼容模式，即使用了@ApiAbility注解
     */
    private int compatibleMode;
}
