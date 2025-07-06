package com.projetinhosgit.rh.config;


import org.springframework.context.annotation.Bean;                  
import org.springframework.context.annotation.Configuration;       
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 

// Essa anotação diz ao Spring que essa classe contém métodos que criam "beans" de configuração
@Configuration
public class SecurityConfig {
    
    // Esse método define um bean do tipo BCryptPasswordEncoder, que ficará disponível para ser injetado em qualquer classe do projeto
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Cria uma nova instância do codificador BCrypt, usado para criptografar senhas
        return new BCryptPasswordEncoder();
    }
}
