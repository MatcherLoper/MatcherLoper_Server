package com.toy.matcherloper.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.toy.matcherloper.event")
public class EventConfig {

    @Bean
    public AsyncEventInitializer asyncEventConfig() {
        return new AsyncEventInitializer();
    }

    @Bean
    public EventResetProcessor eventResetProcessor() {
        return new EventResetProcessor();
    }
}
