package com.linewell.jiceng.core.controller;

import com.linewell.jiceng.core.bean.ApiMetaBuilder;
import com.linewell.jiceng.core.bean.ServiceApiInfo;
import com.linewell.jiceng.core.route.ServiceRouteInfo;
import com.linewell.jiceng.core.util.OpenUtil;
import com.linewell.jiceng.core.util.ServiceRouteInfoBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 *  @author wping created on 2021-01-29 13:47
 */
@Slf4j
@RestController
public class ServiceRouteController {


    @Value("${jiceng.secret:a3d9sf!1@odl90zd>fkASwq}")
    private String secret;
    private static final ApiMetaBuilder apiMetaBuilder = new ApiMetaBuilder();


    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private Environment environment;


    @RequestMapping("/sop/routes")
    public ServiceRouteInfo listRoutes(HttpServletRequest request, HttpServletResponse response) {
        if (!OpenUtil.validateSimpleSign(request, secret)) {
            log.error("签名验证失败, params:{}", request.getQueryString());
            return null;
        }
        return getServiceRouteInfo(request, response);
    }

    protected ServiceRouteInfo getServiceRouteInfo(HttpServletRequest request, HttpServletResponse response) {
        String serviceId = environment.getProperty("spring.application.name");
        if (serviceId == null) {
            throw new IllegalArgumentException("未指定spring.application.name参数");
        }
        ServiceApiInfo serviceApiInfo = apiMetaBuilder.getServiceApiInfo(serviceId, requestMappingHandlerMapping);
        ServiceRouteInfoBuilder serviceRouteInfoBuilder = new ServiceRouteInfoBuilder(environment);
        return serviceRouteInfoBuilder.build(serviceApiInfo);
    }

}
