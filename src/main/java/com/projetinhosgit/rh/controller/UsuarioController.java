package com.projetinhosgit.rh.controller;

import com.projetinhosgit.rh.model.Usuario;
import com.projetinhosgit.rh.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Cadastro de novo usuário
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.loginJaExiste(usuario.getLogin())) {
            return ResponseEntity.status(409).body("Login já está em uso.");
        }

        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    // Login (autenticação simples)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String login, @RequestParam String senha) {
        boolean autenticado = usuarioService.autenticar(login, senha);

        if (autenticado) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(401).body("Login ou senha inválidos.");
        }
    }

    // Atualização de senha
    @PutMapping("/senha")
    public ResponseEntity<String> atualizarSenha(@RequestParam String login,
                                                 @RequestParam String senhaAtual,
                                                 @RequestParam String novaSenha) {

        String resultado = usuarioService.atualizarSenha(login, senhaAtual, novaSenha);

        if (resultado.contains("sucesso")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(401).body(resultado);
        }
    }
}
