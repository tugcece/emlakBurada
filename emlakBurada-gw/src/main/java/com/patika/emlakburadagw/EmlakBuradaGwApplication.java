package com.patika.emlakburadagw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmlakBuradaGwApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakBuradaGwApplication.class, args);
    }

}
