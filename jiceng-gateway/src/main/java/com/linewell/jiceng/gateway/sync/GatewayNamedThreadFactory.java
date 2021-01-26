package com.linewell.jiceng.gateway.sync;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/***
 *  @author wping created on 2021-01-26 15:32 
 */
public class GatewayNamedThreadFactory implements ThreadFactory  {


    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    public GatewayNamedThreadFactory(String name) {

        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (null == name || name.isEmpty()) {
            name = "pool";
        }

        namePrefix = name + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
