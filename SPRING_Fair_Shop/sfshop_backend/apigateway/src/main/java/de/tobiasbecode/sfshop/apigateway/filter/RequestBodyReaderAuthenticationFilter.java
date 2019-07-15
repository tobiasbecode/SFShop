package de.tobiasbecode.sfshop.apigateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiasbecode.sfshop.apigateway.logindto.LoginRequest;
import de.tobiasbecode.sfshop.apigateway.oauth2.Oauth2PasswordAuthenticationProperties;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;


/**
 * Custom RequestBodyReaderAuthenticationFilter
 *
 */

public class RequestBodyReaderAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Log LOG = LogFactory.getLog(RequestBodyReaderAuthenticationFilter.class);

    @Getter
    @Setter
    private UsernamePasswordAuthenticationToken token;

    private final Oauth2PasswordAuthenticationProperties properties;

    //constructor injection of properties with TokenUri, ClientId, Client Secret
    public RequestBodyReaderAuthenticationFilter() {
        properties = new Oauth2PasswordAuthenticationProperties();
    }


    // Transforms Login Request Body (username/password) to UsernamePasswordAuthenticationToken
    // Authenticates Token with Authentication Manager

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestBody;
        try {
            requestBody = IOUtils.toString(request.getReader());
            LoginRequest authRequest = objectMapper.readValue(requestBody, LoginRequest.class);

            properties.setUsername(authRequest.username);

            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password);
            setDetails(request, token);


            return this.getAuthenticationManager().authenticate(token);

        } catch(IOException e) {
            LOG.error(ERROR_MESSAGE, e);
            throw new InternalAuthenticationServiceException(ERROR_MESSAGE, e);
        }
    }
}
