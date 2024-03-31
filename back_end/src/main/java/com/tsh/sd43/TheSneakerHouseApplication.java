package com.tsh.sd43;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TheSneakerHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheSneakerHouseApplication.class, args);
    }

}
