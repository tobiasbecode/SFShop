package de.tobiasbecode.sfshop.apigateway.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

/**
 * Configuration of OAuth2 authorization server access.
 */
@ConfigurationProperties("application.oauth2")
@Getter
@Setter
public class Oauth2PasswordAuthenticationProperties {

    // URI to endpoint of Authorization Server
    private URI accessTokenUri;

    //ClientId and ClientSecret registered in Authorization Server
    private String clientId;
    private String clientSecret;

    //Stores Token from OAuth2-Server
    private String token;
    private String username;

}
