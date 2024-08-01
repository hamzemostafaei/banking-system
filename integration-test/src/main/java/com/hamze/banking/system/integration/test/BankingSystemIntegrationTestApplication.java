package com.hamze.banking.system.integration.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.hamze.banking.system")
public class BankingSystemIntegrationTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankingSystemIntegrationTestApplication.class, args);
    }
}
