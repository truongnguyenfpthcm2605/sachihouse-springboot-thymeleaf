package com.shachi.shachihouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shachi.shachihouse")
public class ShachiHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShachiHouseApplication.class, args);
    }

}
