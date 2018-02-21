package com.omniesoft.commerce.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * @author Vitalii Martynovskyi
 * @since 19.09.17
 */
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = "com.omniesoft.commerce.persistence")
@EnableJpaRepositories("com.omniesoft.commerce.persistence")
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}