package com.ua.searchservice.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.searchservice.entity.user.request.SignInRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper mapper;

    public AuthenticationFilter(ObjectMapper mapper, AuthenticationManager manager) {
        this.mapper = mapper;
        setAuthenticationManager(manager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(req, res);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        SignInRequest credentials;
        try {
            credentials = mapper.readValue(request.getInputStream(), SignInRequest.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword()
        );
        return getAuthenticationManager().authenticate(authToken);
    }
}