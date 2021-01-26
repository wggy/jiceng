package com.linewell.jiceng.gateway.config;

import com.linewell.jiceng.gateway.handler.GatewayExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/***
 *  @author wping created on 2021-01-26 16:03 
 */
@Slf4j
public class BaseGatewayConfiguration extends AbstractConfiguration {

    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     *
     * @param viewResolversProvider viewResolversProvider
     * @param serverCodecConfigurer serverCodecConfigurer
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler sopErrorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                                ServerCodecConfigurer serverCodecConfigurer) {

        GatewayExceptionHandler jsonExceptionHandler = new GatewayExceptionHandler();
        jsonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return jsonExceptionHandler;
    }
}
