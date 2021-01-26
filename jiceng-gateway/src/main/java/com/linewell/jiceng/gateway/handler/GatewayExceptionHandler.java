package com.linewell.jiceng.gateway.handler;

import com.linewell.jiceng.gateway.param.ApiParam;
import com.linewell.jiceng.gateway.util.JiCengConstants;
import com.linewell.jiceng.gateway.util.ServerWebExchangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/***
 *  @author wping created on 2021-01-26 16:05 
 */
@Slf4j
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        ApiParam apiParam = ServerWebExchangeUtil.getApiParam(exchange);
        // 错误记录
        Object routeDefinition = exchange.getAttribute(JiCengConstants.CACHE_ROUTE_INFO);
        log.error("网关报错，参数:{}, 错误信息:{}, 路由信息:{}", apiParam, ex.getMessage(), routeDefinition, ex);
        // 参考AbstractErrorWebExceptionHandler
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), (serverRequest) -> this.renderErrorResponse(ex.toString())).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));

    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageReaders messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param viewResolvers viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     *
     * @param result 返回结果
     * @return 返回mono
     */
    protected Mono<ServerResponse> renderErrorResponse(String result) {
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result));
    }


    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param exchange exchange
     * @param response response
     * @return 返回Mono
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return GatewayExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return GatewayExceptionHandler.this.viewResolvers;
        }

    }
}
