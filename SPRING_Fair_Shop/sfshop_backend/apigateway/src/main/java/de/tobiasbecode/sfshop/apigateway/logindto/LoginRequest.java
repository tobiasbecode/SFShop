package de.tobiasbecode.sfshop.apigateway.logindto;

import org.springframework.stereotype.Component;

@Component
public class LoginRequest {
    public String username;
    public String password;
}
