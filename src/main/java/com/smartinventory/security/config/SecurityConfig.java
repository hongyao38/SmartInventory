package com.smartinventory.security.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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
            .antMatchers("/users").permitAll()
            .and()
        .authenticationProvider(authenticationProvider())
        .csrf().disable() // CSRF protection is needed only for browser based attacks
        .formLogin()
            //using custom login page
            .loginPage("/login")
            .usernameParameter("email")
            .permitAll()
        .and()
        .rememberMe()
            .tokenRepository(PersistentTokenRepo())
        .and()
        .headers().disable();
        return http.build();
    }

    @Bean
    public PersistentTokenRepository PersistentTokenRepo() {
        JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
        tokenRepo.setDataSource(dataSource);
        return tokenRepo;
    }


}
