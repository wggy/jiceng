package com.linewell.jiceng.core.bean;

import com.linewell.jiceng.core.annotation.BizService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 *  @author wping created on 2021-01-29 13:54
 */
public class ApiMetaBuilder {

    private static final String DEFAULT_NAME = "default";

    public ServiceApiInfo getServiceApiInfo(String serviceId, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        if (serviceId == null) {
            throw new IllegalArgumentException("请在application.properties中指定spring.application.name属性");
        }
        List<ServiceApiInfo.ApiMeta> apis = this.buildApiMetaList(requestMappingHandlerMapping);
        ServiceApiInfo serviceApiInfo = new ServiceApiInfo();
        serviceApiInfo.setServiceId(serviceId);
        serviceApiInfo.setApis(apis);
        return serviceApiInfo;
    }

    protected List<ServiceApiInfo.ApiMeta> buildApiMetaList(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = handlerMethods.keySet();
        List<String> store = new ArrayList<>();
        List<ServiceApiInfo.ApiMeta> apis = new ArrayList<>(requestMappingInfos.size());

        for (Map.Entry<RequestMappingInfo, HandlerMethod> handlerMethodEntry : handlerMethods.entrySet()) {
            ServiceApiInfo.ApiMeta apiMeta = this.buildApiMeta(handlerMethodEntry);
            if (apiMeta == null) {
                continue;
            }
            String path = apiMeta.getPath();
            if (store.contains(path)) {
                throw new IllegalArgumentException("重复申明接口，请检查path:" + apiMeta.getPath());
            } else {
                store.add(path);
            }
            apis.add(apiMeta);
        }
        return apis;
    }

    protected ServiceApiInfo.ApiMeta buildApiMeta(Map.Entry<RequestMappingInfo, HandlerMethod> handlerMethodEntry) {
        RequestMappingInfo requestMappingInfo = handlerMethodEntry.getKey();
        Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
        BizService bizService = handlerMethodEntry.getValue().getMethodAnnotation(BizService.class);
        ApiOperation apiOperation = handlerMethodEntry.getValue().getMethodAnnotation(ApiOperation.class);
        ServiceApiInfo.ApiMeta apiMeta = null;
        if (bizService != null) {
            // 方法完整的path，如: /goods/listGoods,/users/user/get
            String path = patterns.iterator().next();
            if (apiOperation != null) {
                apiMeta = new ServiceApiInfo.ApiMeta(apiOperation.value(), path, bizService.needToken());
            } else {
                apiMeta = new ServiceApiInfo.ApiMeta(DEFAULT_NAME, path, bizService.needToken());
            }
        }
        return apiMeta;
    }


}
