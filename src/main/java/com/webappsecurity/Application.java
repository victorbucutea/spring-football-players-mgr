package com.webappsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//This is a convenience annotation that is equivalent to 
//declaring @Configuration, @EnableAutoConfiguration and @ComponentScan.
@ComponentScan(basePackages = "com.webappsecurity")
@SpringBootApplication

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}