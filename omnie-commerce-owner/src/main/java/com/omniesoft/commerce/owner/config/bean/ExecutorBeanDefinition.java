package com.omniesoft.commerce.owner.config.bean;

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
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(1500);
        executor.setThreadNamePrefix("RestTemplateServiceLookup-");
        executor.initialize();
        return executor;

    }
}