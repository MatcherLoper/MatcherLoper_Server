package com.toy.matcherloper.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.toy.matcherloper.auth")
@Import(value = {CoreConfig.class, WebMvcConfig.class, SecurityConfig.class})
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfig {
}
