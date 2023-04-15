package ru.hogwarts.school.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestConfiguration {
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>
            ("postgres")
            .withUsername("postgres")
            .withPassword("test");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
