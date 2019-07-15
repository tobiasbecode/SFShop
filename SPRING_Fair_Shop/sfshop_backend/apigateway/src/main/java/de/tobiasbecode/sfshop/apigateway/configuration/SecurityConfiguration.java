package de.tobiasbecode.sfshop.apigateway.configuration;

import de.tobiasbecode.sfshop.apigateway.filter.RequestBodyReaderAuthenticationFilter;
import de.tobiasbecode.sfshop.apigateway.oauth2.OAuth2PasswordAuthenticationProvider;
import de.tobiasbecode.sfshop.apigateway.oauth2.Oauth2PasswordAuthenticationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static java.util.Collections.singletonList;

/**
 * Security configuration of API gateway.
 *
 * Based on Open-Source Repository with Modifications
 *
 * See also:
 * github.com/viadee/DeicheFuerDieInseln
 * BSD 3-Clause License
 * Copyright (c) 2019, viadee IT-Unternehmensberatung AG
 * All rights reserved.
 */


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(Oauth2PasswordAuthenticationProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Oauth2PasswordAuthenticationProperties properties;
    private final ObjectMapper objectMapper;


    @Autowired
    public SecurityConfiguration(Oauth2PasswordAuthenticationProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() {
        ProviderManager providerManager = new ProviderManager(singletonList(new OAuth2PasswordAuthenticationProvider(properties)));
        return providerManager;
    }


    // Modification:
    // Custom RequestBodyReaderAuthenticationFilter with LoginSuccessHandler and LoginFailureHandler
    // Integrates Authentication Manager Bean

    @Bean
    public RequestBodyReaderAuthenticationFilter authenticationFilter() throws Exception {
        RequestBodyReaderAuthenticationFilter authenticationFilter
                = new RequestBodyReaderAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }


    //Modification: Added CSRF Protection
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(
                        authenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutSuccessHandler(this::logoutSuccessHandler)

                .and().httpBasic()
                .and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }


    // Modification
    // Custom LoginSuccessHandler - Integrates Token from OAuth2PasswordAuthenticationProvider
    // and sends Token as JSON Response
    private void loginSuccessHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        response.setContentType("text/json; charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), properties.getToken());
    }


    //Custom Login Failure Handler
    private void loginFailureHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), "Login fehlgeschlagen");
    }

    //Custom Logout Handler
    private void logoutSuccessHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "Logout erfolgreich");
    }


}