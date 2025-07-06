package com.projetinhosgit.rh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetinhosgit.rh.dto.LoginRequest;
import com.projetinhosgit.rh.service.UsuarioService;

//Controlador REST que expõe endpoints via HTTP, com resposta JSON
@RestController
// Prefixo comum para todas as rotas deste controller (ex: /api/login)
@RequestMapping("/api")
public class LoginController {

    // Injeta o serviço que contém a lógica de autenticação
    private final UsuarioService usuarioService;

    // Construtor com injeção de dependência
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint POST para autenticação de usuários
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Chama o serviço para verificar se login e senha são válidos
        boolean autenticado = usuarioService.autenticar(
            loginRequest.getLogin(), 
            loginRequest.getSenha()
        );

        // Se ok, retorna status 200 com mensagem de sucesso
        if (autenticado) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            // Se inválido, retorna status 401 (unauthorized)

        	return ResponseEntity.status(401).body("Login ou senha inválidos.");
        }
    }
}
