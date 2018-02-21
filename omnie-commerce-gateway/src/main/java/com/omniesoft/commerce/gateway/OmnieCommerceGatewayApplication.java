package com.omniesoft.commerce.gateway;

import com.omniesoft.commerce.gateway.zuul.ResponseHeaderAppender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableZuulProxy
@EnableSwagger2
@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableDiscoveryClient
public class OmnieCommerceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmnieCommerceGatewayApplication.class, args);
    }

    @Bean
    public ResponseHeaderAppender responseHeaderAppender() {
        return new ResponseHeaderAppender();
    }


}

