package com.smartinventory.security.config;

import javax.sql.DataSource;

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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userService;
    private final BCryptPasswordEncoder encoder;
    private DataSource dataSource;

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
        .authorizeRequests()
            // Authentication NOT NEEDED 
            .antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/registration").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/registration/confirm?token=**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/forget-password").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/v1/forget-password/reset?token=**").permitAll()

            // Authentication NEEDED
            .antMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/api/v1/users").authenticated()
            
            .and()
        .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(authenticationProvider())
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .formLogin().disable()
        // .rememberMe()
        //     .tokenRepository(PersistentTokenRepo())
        // .and()
        .headers().disable();
        return http.build();
    }

    // @Bean
    // public PersistentTokenRepository PersistentTokenRepo() {
    //     JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
    //     tokenRepo.setDataSource(dataSource);
    //     return tokenRepo;
    // }


}
