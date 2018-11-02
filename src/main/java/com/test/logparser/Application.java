package com.test.logparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

//    @Autowired
//    private Processor processor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
