package com.linewell.jiceng.gateway.route;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/***
 *  @author wping created on 2021-01-25 15:41 
 */
@Slf4j
public class ServiceRouteListener extends BaseServiceListener {
    private static final String METADATA_SOP_ROUTES_PATH = "gateway.routes.path";
    private static final String SOP_ROUTES_PATH = "/gateway/routes";

    @Autowired
    private GatewayRouteCache gatewayRouteCache;
    @Autowired
    private RoutesProcessor routesProcessor;


    @Override
    public void onRemoveService(String serviceId) {
        log.info("服务下线，删除路由配置，serviceId: {}", serviceId);
        gatewayRouteCache.remove(serviceId);
        routesProcessor.removeAllRoutes(serviceId);
    }

    @Override
    public void onAddInstance(InstanceDefinition instance) {
        String serviceName = instance.getServiceId();
        String url = getRouteRequestUrl(instance);
        log.info("拉取路由配置，serviceId: {}, url: {}", serviceName, url);
        ResponseEntity<String> responseEntity = getRestTemplate().getForEntity(url, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            ServiceRouteInfo serviceRouteInfo = JSON.parseObject(body, ServiceRouteInfo.class);
            gatewayRouteCache.load(serviceRouteInfo, callback -> routesProcessor.saveRoutes(serviceRouteInfo, instance));
        } else {
            log.error("拉取路由配置异常，url: {}, status: {}, body: {}", url, responseEntity.getStatusCodeValue(), responseEntity.getBody());
        }
    }

    private String getRouteRequestUrl(InstanceDefinition instance) {
        Map<String, String> metadata = instance.getMetadata();
        String customPath = metadata.get(METADATA_SOP_ROUTES_PATH);
        String homeUrl;
        String servletPath;
        // 如果metadata中指定了获取路由的url
        if (StringUtils.isNotBlank(customPath)) {
            // 自定义完整的url
            if (customPath.startsWith("http")) {
                homeUrl = customPath;
                servletPath = "";
            } else {
                homeUrl = getHomeUrl(instance);
                servletPath = customPath;
            }
        } else {
            // 默认处理
            homeUrl = getHomeUrl(instance);
            String contextPath = this.getContextPath(metadata);
            servletPath = contextPath + SOP_ROUTES_PATH;
        }
        if (StringUtils.isNotBlank(servletPath) && !servletPath.startsWith("/")) {
            servletPath = '/' + servletPath;
        }
        String query = buildQuery();
        return homeUrl + servletPath + query;
    }


    private static String getHomeUrl(InstanceDefinition instance) {
        return "http://" + instance.getIp() + ":" + instance.getPort();
    }
}
