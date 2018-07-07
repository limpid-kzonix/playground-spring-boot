package com.omniesoft.commerce.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Vitalii Martynovskyi
 * @since 18.09.17
 */

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = "com.omniesoft.commerce.persistence")
@EnableJpaRepositories("com.omniesoft.commerce.persistence")
@ComponentScan({"com.omniesoft.commerce.common.component.notification"})
@EnableScheduling
@EnableDiscoveryClient
@EnableEurekaClient
public class NotificationApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NotificationApplication.class);
    }

}
