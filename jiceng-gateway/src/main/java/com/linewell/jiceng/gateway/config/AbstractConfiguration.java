package com.linewell.jiceng.gateway.config;

import com.linewell.jiceng.gateway.loadbalancer.NacosServerIntrospector;
import com.linewell.jiceng.gateway.monitor.MonitorManager;
import com.linewell.jiceng.gateway.route.RegistryListener;
import com.linewell.jiceng.gateway.route.ServiceListener;
import com.linewell.jiceng.gateway.route.ServiceRouteListener;
import com.linewell.jiceng.gateway.sync.GatewayAsyncConfigurer;
import com.linewell.jiceng.gateway.util.SpringContextUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 *  @author wping created on 2021-01-22 16:29 
 */
public abstract class AbstractConfiguration implements ApplicationContextAware, ApplicationRunner {

    private volatile boolean isStarted;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    protected ApplicationContext applicationContext;


    @Autowired
    private RegistryListener registryListener;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }


    @Bean
    @ConditionalOnMissingBean
    ServiceListener serviceListener() {
        return new ServiceRouteListener();
    }

    @Bean
    public AsyncConfigurer sopAsyncConfigurer(@Value("${gateway.monitor-route-interceptor.thread-pool-size:5}")
                                                         int threadPoolSize) {
        return new GatewayAsyncConfigurer("gatewayAsync", threadPoolSize);
    }

    @Bean
    @ConditionalOnMissingBean
    public MonitorManager monitorManager() {
        return new MonitorManager();
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty("jiceng.gateway-index-path")
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", createCorsConfiguration());
        return new CorsWebFilter(source);
    }

    private CorsConfiguration createCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }


    /**
     * 负责获取nacos实例的metadata
     * @return
     */
    @Bean
    @ConditionalOnProperty("spring.cloud.nacos.discovery.server-addr")
    ServerIntrospector nacosServerIntrospector() {
        return new NacosServerIntrospector();
    }

    @EventListener(classes = HeartbeatEvent.class)
    public void listenEvent(ApplicationEvent heartbeatEvent) {
        if (!isStarted) {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        registryListener.onEvent(heartbeatEvent);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.isStarted = true;
        lock.lock();
        condition.signalAll();
        lock.unlock();
        after();
    }

    @PostConstruct
    private void post() {
        SpringContextUtil.setApplicationContext(applicationContext);
    }

    private void after() {

    }
}
