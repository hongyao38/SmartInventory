package com.smartinventory.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userService;
    private final BCryptPasswordEncoder encoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder);

        return authProvider;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .httpBasic()
            .and()
        .cors()
            .and()
        .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
            // Authentication NOT NEEDED 
            .antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
            .antMatchers(HttpMethod.GET, "api/v1/users/check-username").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/registration").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/registration/confirm?token=**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/forget-password").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/v1/forget-password/reset?token=**").permitAll()

            // Authentication NEEDED
            .antMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")

            //authentication for storage
            .antMatchers(HttpMethod.POST, "/api/v1/**").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/api/v1/**").hasRole("USER")

            // //authentication for food
            // .antMatchers(HttpMethod.POST, "/api/v1/food").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/food").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/food/**").hasRole("USER")
            // .antMatchers(HttpMethod.DELETE, "/api/v1/food/**").hasRole("USER")

            // //authentication for consumption
            // .antMatchers(HttpMethod.POST, "/api/v1/food/*/consumptions").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/consumptions").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/food/*/consumptions/*").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/food/*/consumptions/*").hasRole("USER")
            // .antMatchers(HttpMethod.PUT, "/api/v1/food/*/consumptions/*").hasRole("USER")
            // .antMatchers(HttpMethod.DELETE, "/api/v1/food/*/consumptions/*").hasRole("USER")

            // //authentication for purchase
            // .antMatchers(HttpMethod.POST, "/api/v1/food/*/purchases").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/purchases").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/food/*/purchases").hasRole("USER")
            // .antMatchers(HttpMethod.GET, "/api/v1/food/*/purchases/*").hasRole("USER")
            // .antMatchers(HttpMethod.PUT, "/api/v1/food/*/purchases/*").hasRole("USER")
            // .antMatchers(HttpMethod.DELETE, "/api/v1/food/*/purchases/*").hasRole("USER")

            //authentication for container
            .antMatchers(HttpMethod.POST, "/api/v1/containers/**").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/api/v1/containers/**").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/api/v1/containers/**/**").hasRole("USER")
            .antMatchers(HttpMethod.DELETE, "/api/v1/food/{foodId}containers/*").hasRole("USER")
            
            .and()
        .authenticationProvider(authenticationProvider())
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .formLogin().disable()
        .headers().disable();
        return http.build();
    }
}
