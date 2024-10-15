package com.example.sprestocknotiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpRestockNotiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpRestockNotiServiceApplication.class, args);
    }

}
