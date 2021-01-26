package com.linewell.jiceng.gateway.util;

import org.springframework.context.ApplicationContext;

/***
 *  @author wping created on 2021-01-26 16:00 
 */
public class SpringContextUtil {

    private static ApplicationContext ctx;

    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    public static void setApplicationContext(ApplicationContext ctx) {
        SpringContextUtil.ctx = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
