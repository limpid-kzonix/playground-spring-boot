package com.omniesoft.commerce.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OmnieCommerceDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmnieCommerceDiscoveryApplication.class, args);
    }
}
