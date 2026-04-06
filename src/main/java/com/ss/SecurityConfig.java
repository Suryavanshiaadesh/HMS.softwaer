package com.ss;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())   // disable CSRF for dev

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws/**").permitAll() // allow websocket
                .anyRequest().permitAll()              
            );

        return http.build();
    }
}