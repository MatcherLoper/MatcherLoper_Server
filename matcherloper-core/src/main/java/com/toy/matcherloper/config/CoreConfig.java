package com.toy.matcherloper.config;

import com.toy.matcherloper.event.config.EventConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.toy.matcherloper.core")
@Import(value = {JpaConfiguration.class, QueryDslConfiguration.class, EventConfig.class})
public class CoreConfig {
}
