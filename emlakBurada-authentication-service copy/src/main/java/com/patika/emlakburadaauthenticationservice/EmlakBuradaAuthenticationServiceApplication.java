package com.patika.emlakburadaauthenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class EmlakBuradaAuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakBuradaAuthenticationServiceApplication.class, args);
    }

}
