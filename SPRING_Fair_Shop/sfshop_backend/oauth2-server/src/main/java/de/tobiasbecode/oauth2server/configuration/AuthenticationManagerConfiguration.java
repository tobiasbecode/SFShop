package de.tobiasbecode.oauth2server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static java.util.Arrays.asList;

/**
 * InMemoryUserDetailsManager
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
public class AuthenticationManagerConfiguration{

    @Bean
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(new User("admin", passwordEncoder().encode("secret"),
                asList( new SimpleGrantedAuthority("ADMIN"))));
        userDetailsManager.createUser(new User("user", passwordEncoder().encode("password"),
                asList( new SimpleGrantedAuthority("USER"))));
        return userDetailsManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
