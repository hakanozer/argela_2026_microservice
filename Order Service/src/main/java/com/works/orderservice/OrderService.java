package com.works.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrderService {

    public static void main(String[] args) {
        SpringApplication.run(OrderService.class, args);
    }

}
