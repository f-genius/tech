package ru.shev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.shev.*")
@EntityScan("ru.shev.*")
@EnableJpaRepositories("ru.shev.repositories")
public class catApplication {
    public static void main(String[] args) {
        SpringApplication.run(catApplication.class, args);
    }
}
