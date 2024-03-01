package com.shachi.shachihouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.shachi.shachihouse")
@EnableTransactionManagement
@EnableScheduling
public class ShachiHouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShachiHouseApplication.class, args);
    }

}
