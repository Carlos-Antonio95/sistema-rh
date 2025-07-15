package com.projetinhosgit.rh.controller;

import com.projetinhosgit.rh.model.Ferias;
import com.projetinhosgit.rh.service.FeriasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ferias")
public class FeriasController {

    private final FeriasService feriasService;

    public FeriasController(FeriasService feriasService) {
        this.feriasService = feriasService;
    }

    // GET - Listar todas as férias
    @GetMapping
    public ResponseEntity<List<Ferias>> listarTodas() {
        List<Ferias> lista = feriasService.listarTodasFerias();
        return ResponseEntity.ok(lista);
    }

    // GET - Buscar férias por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ferias> buscarPorId(@PathVariable Long id) {
        Optional<Ferias> ferias = feriasService.buscarPorId(id);
        return ferias.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // POST - Cadastrar novo registro de férias
    @PostMapping
    public ResponseEntity<Ferias> salvar(@RequestBody Ferias ferias) {
        try {
            Ferias novaFerias = feriasService.salvarFerias(ferias);
            return ResponseEntity.ok(novaFerias);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // PUT - Atualizar férias
    @PutMapping("/{id}")
    public ResponseEntity<Ferias> atualizar(@PathVariable Long id, @RequestBody Ferias feriasAtualizadas) {
        try {
            Ferias ferias = feriasService.atualizarFerias(id, feriasAtualizadas);
            return ResponseEntity.ok(ferias);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // DELETE - Deletar férias por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        String mensagem = feriasService.deletarPorId(id);
        if (mensagem.contains("foi deletado")) {
            return ResponseEntity.ok(mensagem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
