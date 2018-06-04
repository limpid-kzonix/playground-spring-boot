package com.omniesoft.commerce.user.config.beans;

import com.omniesoft.commerce.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorBeanDefinition {

    @Bean(name = Constants.Executors.ASYNC_HTTP)
    public Executor httpThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(24);
        executor.setQueueCapacity(1500);
        executor.setThreadNamePrefix("RestTemplateServiceLookup-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "scheduleThreadPoolExecutor")
    public Executor scheduleThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ScheduleTemplateServiceLookup-");
        executor.initialize();
        return executor;
    }
}