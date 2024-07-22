package com.patika.emlakburadalistingstatusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmlakBuradaListingStatusServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmlakBuradaListingStatusServiceApplication.class, args);
    }

}
