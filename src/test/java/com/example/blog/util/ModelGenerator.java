package com.example.blog.util;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelGenerator {

    @Bean
    public Faker faker() {
        return new Faker();
    }
}