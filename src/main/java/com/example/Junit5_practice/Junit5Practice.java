package com.example.Junit5_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Junit5Practice {

    public static void main(String[] args) {
        SpringApplication.run(Junit5Practice.class, args);
    }

}
