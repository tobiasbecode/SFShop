package de.tobiasbecode.sfshop.rating.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.http.HttpMethod.*;

/**
 * Oauth2 Resource Server - Connects to sfshop-oauth2
 */


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(GET,"/ratings/*").permitAll()
                .antMatchers(POST,"/ratings/new").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(DELETE,"/ratings/*").hasAuthority("ADMIN")
                .anyRequest().denyAll();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

}