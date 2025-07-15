package com.projetinhosgit.rh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    // Libera todas as rotas sem autenticação
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desativa proteção CSRF (para testes com ferramentas como Insomnia/Postman)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // permite qualquer rota sem autenticação
                .anyRequest().permitAll()
            );

        return http.build();
    }

    // Bean para encriptar senhas (mantém o seu)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
