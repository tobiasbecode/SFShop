package de.tobiasbecode.sfshop.apigateway.oauth2;

import de.tobiasbecode.sfshop.apigateway.filter.RequestBodyReaderAuthenticationFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * Custom authentication provider to forward given username and password to OAuth2
 * authorization server and issue custom OAuth2AccessTokenAuthentication.
 * <p>
 * Based on Open-Source Repository with Modifications/Comments
 * <p>
 * See also:
 * github.com/viadee/DeicheFuerDieInseln
 * BSD 3-Clause License
 * Copyright (c) 2019, viadee IT-Unternehmensberatung AG
 * All rights reserved.
 */


public class OAuth2PasswordAuthenticationProvider implements AuthenticationProvider {

    @Setter
    @Getter
    private String oAuthToken;


    private RestTemplate restTemplate = new RestTemplate();
    private RequestBodyReaderAuthenticationFilter reqFilter = new RequestBodyReaderAuthenticationFilter();

    private final Oauth2PasswordAuthenticationProperties properties;

    //constructor injection of properties with TokenUri, ClientId, Client Secret
    public OAuth2PasswordAuthenticationProvider(Oauth2PasswordAuthenticationProperties properties) {
        this.properties = properties;
    }


    // Method to return AuthenticationToken-Object with AccessToken and Authentication => stored in Session
    // Authentication is received from formBases Login (UsernamePasswordAuthenticationToken)
    // AccessToken is received from Authentication Server
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // if Authentication is not an instance of Authentication implementation of username and password
        // then cast authentication to UsernamePasswordAuthenticationToken and store it in usernamePassword
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }

        final UsernamePasswordAuthenticationToken usernamePassword = (UsernamePasswordAuthenticationToken) authentication;

        // get Principal and credentials from Token, cast it to strings and store it in variables
        final String username = (String) usernamePassword.getPrincipal();
        final String password = (String) usernamePassword.getCredentials();

        // create a "form body" out of username, password and grant type
        final MultiValueMap<String, String> formFields = new LinkedMultiValueMap<>();
        formFields.add("grant_type", "password");
        formFields.add("username", username);
        formFields.add("password", password);

        // get Client Secret from properties
        // get Client Id from properties
        // store it in byte Array for encoding
        final byte[] clientCredentialsBytes = (properties.getClientId() + ":" + properties.getClientSecret()).getBytes(StandardCharsets.US_ASCII);

        // endcode clientcredentials with base64 and store it in string
        final String clientCredentialsEncoded = Base64.getEncoder().encodeToString(clientCredentialsBytes);

        // create RequestEntity with encoded Credentials (encoded ClientId and ClientSecret)
        // and form fields with grant type and username/password
        final RequestEntity request = RequestEntity
                .post(properties.getAccessTokenUri())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + clientCredentialsEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formFields);

        //  return Response from OauthAuthenticationServer as Oauth2AccessToken
        //  Transforms JSON with Deserializer
        final ResponseEntity<OAuth2AccessToken> response = restTemplate.exchange(request, OAuth2AccessToken.class);

        System.out.println(response.getBody().getValue());
        System.out.println(response.getHeaders());

        // Exception Handling for failed Authorization
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new InsufficientAuthenticationException("Authorisierung fehlgeschlagen");
        } else if (response.getStatusCode() != HttpStatus.OK) {
            throw new AuthenticationServiceException("Authorisierung fehlgeschlagen");
        }

        final OAuth2AccessToken oAuth2AccessToken = response.getBody();

        // Token from OAuth2-Server stored in OAuth2PasswordAuthenticationProperties
        OAuth2PasswordAuthenticationProvider.this.setOAuthToken(oAuth2AccessToken.getValue());
        this.properties.setToken(oAuth2AccessToken.getValue());

        return new OAuth2AccessTokenAuthentication(oAuth2AccessToken, authentication);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
