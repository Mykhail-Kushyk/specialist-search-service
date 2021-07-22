package com.ua.searchservice.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ua.searchservice.config.Properties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private Algorithm algorithm;

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        algorithm = Algorithm.HMAC512(new String(Properties.secret.getBytes()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        var securityContext = SecurityContextHolder.getContext();

        var authentication = securityContext.getAuthentication();
        // if already authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(Properties.AUTH_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String encodedJwt = header.substring(Properties.AUTH_PREFIX.length());
        authentication = getAuthentication(encodedJwt);

        securityContext.setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String encodedJwt) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(encodedJwt);
        } catch (Exception e) {
            return null;
        }

        String username = decodedJWT.getSubject();

        Claim claim = decodedJWT.getClaim("authorities");

        List<SimpleGrantedAuthority> authorities = claim.asList(SimpleGrantedAuthority.class);

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}