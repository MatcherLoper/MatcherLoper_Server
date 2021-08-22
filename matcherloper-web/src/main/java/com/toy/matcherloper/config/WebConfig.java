package com.toy.matcherloper.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.toy.matcherloper.event.config.EventConfig;
import com.toy.matcherloper.matching.config.MatchingConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Import 어노테이션으로 외부 모듈의 Configuration 클래스를 가져와서 외부 모듈의 컴포넌트 스캔을 진행한다.
 */
@Configuration
@ComponentScan(basePackages = "com.toy.matcherloper.web")
@Import(value = {CoreConfig.class, EventConfig.class, AuthConfig.class, MatchingConfig.class})
@EnableConfigurationProperties(OAuth2Properties.class)
public class WebConfig {
}
