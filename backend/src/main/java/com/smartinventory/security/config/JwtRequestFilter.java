package com.smartinventory.security.config;

import java.io.IOException;
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
            String username = JwtUtil.getUsername(token);
            Collection<SimpleGrantedAuthority> authorities = JwtUtil.getAuthorities(token);

            // Tell spring boot how to make use of above to authorize
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Continue with filter chain
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setHeader("Error: ", e.getMessage());
            response.sendError(403, "Invalid token"); // FORBIDDEN
        }
    }
}
