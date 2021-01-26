package com.linewell.jiceng.gateway.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 *  @author wping created on 2021-01-26 15:31 
 */

@Slf4j
public class GatewayAsyncConfigurer implements AsyncConfigurer {

    private final ThreadPoolExecutor threadPoolExecutor;

    public GatewayAsyncConfigurer(String threadName, int poolSize) {
        threadPoolExecutor = new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new GatewayNamedThreadFactory(threadName));
    }

    @Override
    public Executor getAsyncExecutor() {
        return threadPoolExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable e, Method method, Object... args) -> {
            log.error("异步运行方法出错, method:{}, args:{}, message:{}", method, Arrays.deepToString(args), e.getMessage(), e);
        };
    }
}
