package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@OpenAPIDefinition
public class HogwartsStudyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(HogwartsStudyWebApplication.class, args);
    }
        @Test
        void contextLoads () {
        }
    }

