package com.eservice.iot.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class TaskExecutePool {
    /**
    *此处成员变量应该使用@Value从配置中读取
    */
    private int corePoolSize = 5;
    private int maxPoolSize = 10;
    private int queueCapacity = 25;

    @Bean(name = "myExecutePool")
    public Executor myExecutePool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("YTTPS-");
        executor.initialize();
        return executor;
    }
}
