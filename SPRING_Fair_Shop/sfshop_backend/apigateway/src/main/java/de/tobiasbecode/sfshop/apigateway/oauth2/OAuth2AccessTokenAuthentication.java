package de.tobiasbecode.sfshop.apigateway.oauth2;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Custom AccessTokenAuthentication
 *
 * Based on Open-Source Repository with Modifications/Comments
 *
 * See also:
 * github.com/viadee/DeicheFuerDieInseln
 * BSD 3-Clause License
 * Copyright (c) 2019, viadee IT-Unternehmensberatung AG
 * All rights reserved.
 */

public class OAuth2AccessTokenAuthentication extends AbstractAuthenticationToken {

    private final OAuth2AccessToken oAuth2AccessToken;
    private final Authentication userAuthentication;

    OAuth2AccessTokenAuthentication(OAuth2AccessToken oAuth2AccessToken, Authentication userAuthentication) {
        super(null);
        this.oAuth2AccessToken = oAuth2AccessToken;
        this.userAuthentication = userAuthentication;
    }

    // Checks the credentials, principal and details objects
    // Remove Credentials inside Credentials Container
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();

        if (userAuthentication instanceof CredentialsContainer) {
            ((CredentialsContainer) userAuthentication).eraseCredentials();
        }
    }

    // Method which returns an Object for check of principal
    // The credentials (in this case the password) are received from accessToken
    @Override
    public Object getCredentials() {
        return oAuth2AccessToken;
    }


    // get Principal from userAuthentication (formBased Login)
    @Override
    public Object getPrincipal() {
        return userAuthentication.getPrincipal();
    }

    public OAuth2AccessToken getoAuth2AccessToken() {
        return oAuth2AccessToken;
    }
}

