package com.toy.matcherloper.config;

import com.toy.matcherloper.auth.jwt.filter.TokenAuthenticationFilter;
import com.toy.matcherloper.auth.oauth2.handler.FailureHandler;
import com.toy.matcherloper.auth.oauth2.handler.SuccessHandler;
import com.toy.matcherloper.auth.oauth2.service.HttpCookieOAuth2AuthorizationRequestRepository;
import com.toy.matcherloper.auth.oauth2.service.OAuth2UserLoadService;
import com.toy.matcherloper.auth.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final OAuth2UserLoadService oAuth2UserLoadService;
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;

    @Bean
    public TokenAuthenticationFilter getJwtFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository getCookieAuthorizationFromRequest(){
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .authorizeRequests()
                        .antMatchers("/api/v1/**").permitAll()
                        .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**").permitAll()
                        .antMatchers("/auth/**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                        .authorizationEndpoint()
                        .baseUri("/oauth2/authorize/google") //첫 로그인
                        .authorizationRequestRepository(getCookieAuthorizationFromRequest())
                .and()
                    .userInfoEndpoint()
                        .userService(oAuth2UserLoadService)
                .and()
                    .successHandler(successHandler)
                    .failureHandler(failureHandler);

        http.addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
