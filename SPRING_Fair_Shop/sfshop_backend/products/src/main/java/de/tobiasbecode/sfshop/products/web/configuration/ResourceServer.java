package de.tobiasbecode.sfshop.products.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


import static org.springframework.http.HttpMethod.*;

/**
 * Oauth2 Resource Server - Connects to sfshop-oauth2 /
 * uses Authorities for fine tuning of certain requests
 */

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(GET,"/products/*").permitAll()
                .antMatchers(POST,"/products/new").hasAuthority("ADMIN")
                .antMatchers(DELETE,"/products/delete/*").hasAuthority("ADMIN")
                .anyRequest().denyAll();
    }
}
