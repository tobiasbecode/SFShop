package de.tobiasbecode.sfshop.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import de.tobiasbecode.sfshop.apigateway.oauth2.OAuth2AccessTokenAuthentication;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * Custom AuthorizationHeaderFilter
 *
 * Based on Open-Source Repository with Modifications/Comments
 *
 * See also:
 * github.com/viadee/DeicheFuerDieInseln
 * BSD 3-Clause License
 * Copyright (c) 2019, viadee IT-Unternehmensberatung AG
 * All rights reserved.
 */

@Component
public class AuthorizationHeaderFilter extends ZuulFilter {

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();

        //Remove Authorization Header
        ctx.getZuulRequestHeaders().remove(HttpHeaders.AUTHORIZATION);

        // If the user has been authenticated => Get Authentication Object via SecurityContextHolder
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AccessTokenAuthentication) {
            final OAuth2AccessToken accessToken = ((OAuth2AccessTokenAuthentication) authentication).getoAuth2AccessToken();

            if (accessToken != null) {
                ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, accessToken.getTokenType() + ' ' + accessToken.getValue());
            }
        }

        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }
}
