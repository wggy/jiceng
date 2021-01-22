package com.linewell.jiceng.gateway.config;

import com.linewell.jiceng.gateway.route.RegistryListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

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

    private void after() {

    }
}
