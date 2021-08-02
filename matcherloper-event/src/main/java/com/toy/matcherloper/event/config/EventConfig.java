package com.toy.matcherloper.event.config;

import com.toy.matcherloper.config.CoreConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.toy.matcherloper.event")
@Import(value = {CoreConfig.class})
public class EventConfig {
}
