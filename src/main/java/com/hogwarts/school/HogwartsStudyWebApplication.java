package com.hogwarts.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@OpenAPIDefinition
public class HogwartsStudyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HogwartsStudyWebApplication.class, args);
    }

}
