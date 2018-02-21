package com.omniesoft.commerce.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableMongoRepositories
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class OmnieStatisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmnieStatisticApplication.class, args);
    }

}
