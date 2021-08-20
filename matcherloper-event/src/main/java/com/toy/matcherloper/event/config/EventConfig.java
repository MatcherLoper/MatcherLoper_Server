package com.toy.matcherloper.event.config;

import com.toy.matcherloper.matching.config.MatchingConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.toy.matcherloper.event")
@Import(value = {MatchingConfig.class})
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
