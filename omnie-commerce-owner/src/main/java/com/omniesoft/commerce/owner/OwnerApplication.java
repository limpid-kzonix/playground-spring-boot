package com.omniesoft.commerce.owner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Vitalii Martynovskyi
 * @since 18.09.17
 */
@SpringBootApplication
@EntityScan(basePackages = "com.omniesoft.commerce.persistence")
@EnableJpaRepositories("com.omniesoft.commerce.persistence")
@EnableCaching
@EnableAsync
@EnableScheduling
@Slf4j
@EnableDiscoveryClient
@EnableEurekaClient
public class OwnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnerApplication.class, args);
    }

}
