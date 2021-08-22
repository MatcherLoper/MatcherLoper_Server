package com.toy.matcherloper.matching.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.toy.matcherloper.matching")
@Import(value = {FirebaseConfig.class})
public class MatchingConfig {
}
