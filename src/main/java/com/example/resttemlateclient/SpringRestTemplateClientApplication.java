package com.example.resttemlateclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringRestTemplateClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestTemplateClientApplication.class, args);
    }

}
