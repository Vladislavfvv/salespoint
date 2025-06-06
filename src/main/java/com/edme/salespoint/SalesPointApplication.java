package com.edme.salespoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching  // Включает механизм кеширования для Redis
@ComponentScan(basePackages = "com.edme.salespoint")
@EntityScan(basePackages = "com.edme.salespoint.models")
@EnableJpaRepositories(basePackages = "com.edme.salespoint.repository")
@EnableDiscoveryClient
public class SalesPointApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesPointApplication.class, args);
    }

}
