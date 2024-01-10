package com.example.catalogsevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CatalogSeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogSeviceApplication.class, args);
    }

}
