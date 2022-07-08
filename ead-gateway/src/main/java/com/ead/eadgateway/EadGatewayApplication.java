package com.ead.eadgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EadGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EadGatewayApplication.class, args);
    }

}
