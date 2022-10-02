package com.smartinventory.security.config;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // No need to authorize if user is doing these
        if (request.getServletPath().equals("/api/v1/login") ||
            request.getServletPath().startsWith("/api/v1/registration") ||
            request.getServletPath().startsWith("/api/v1/forget-password")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String authorizationHeader = request.getHeader("Authorization");

        // If authorization header does not start with "Bearer ", just continue
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(400, "Missing Authorization header"); // BAD REQUEST (Not correct format)
            return;
        }

        // Break down JWT
        try {

            String token = authorizationHeader.substring("Bearer ".length()); // Remove "Bearer "
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();

            // Break down JWT into username and authorities of the user
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });

            // Tell spring boot how to make use of above to authorize
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Continue with filter chain
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            System.out.println(String.format("Error logging in: ", e.getMessage()));
            response.setHeader("Error: ", e.getMessage());
            response.sendError(403, "Invalid token"); // FORBIDDEN
        }
    }
}
