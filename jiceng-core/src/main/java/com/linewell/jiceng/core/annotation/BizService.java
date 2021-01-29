package com.linewell.jiceng.core.annotation;

import java.lang.annotation.*;

/***
 *  @author wping created on 2021-01-29 14:01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizService {

    /**
     * 是否需要appAuthToken，设置为true，网关端会校验token是否存在
     */
    boolean needToken() default true;

}
