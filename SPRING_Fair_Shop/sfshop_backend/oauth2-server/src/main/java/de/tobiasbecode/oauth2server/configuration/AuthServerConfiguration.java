package de.tobiasbecode.oauth2server.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


/**
 * AuthServerConfiguration
 *
 * Based on Open-Source Repository with Modifications/Comments
 *
 * See also:
 * github.com/viadee/DeicheFuerDieInseln
 * BSD 3-Clause License
 * Copyright (c) 2019, viadee IT-Unternehmensberatung AG
 * All rights reserved.
 */


@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {


    //Modification: Properties List for better overview
    static final String CLIENT_ID = "api-gateway";
    //static final String CLIENT_SECRET = "angularwebshop";
    static final String CLIENT_SECRET = "secret";
    static final String CLIENT_CREDENTIALS = "client_credentials";
    static final String GRANT_TYPE_PASSWORD = "password";
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60;
    static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;


    private final AuthenticationConfiguration authenticationConfiguration;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServerConfiguration(AuthenticationConfiguration authenticationConfiguration, PasswordEncoder passwordEncoder) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    //verify tokens of resources with endpoints /oauth/check_token and /oauth/token_key
    @Override
    public void configure(AuthorizationServerSecurityConfigurer authSecurityConf) throws Exception {
        authSecurityConf
                .passwordEncoder(passwordEncoder)
                .checkTokenAccess("hasAuthority('CHECK_AUTH_TOKEN')");
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .authenticationManager(authenticationConfiguration.getAuthenticationManager())
                .tokenStore(tokenStore())
        ;
    }


    @Bean
    public InMemoryTokenStore tokenStore() {
        return new InMemoryTokenStore();
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsConf) throws Exception {
        clientDetailsConf
                .inMemory()
                .withClient(CLIENT_ID)
                    .secret(passwordEncoder.encode(CLIENT_SECRET))
                    .authorizedGrantTypes(CLIENT_CREDENTIALS, GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
                    .scopes("read", "write","delete")
                    .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                    .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS)

                .and()
                    .withClient("products")
                    .secret(passwordEncoder.encode("test"))
                    .authorities("CHECK_AUTH_TOKEN")
                .and()
                    .withClient("order")
                    .secret(passwordEncoder.encode("test"))
                    .authorities("CHECK_AUTH_TOKEN")
                 .and()
                    .withClient("rating")
                    .secret(passwordEncoder.encode("test"))
                    .authorities("CHECK_AUTH_TOKEN");
    }
}
