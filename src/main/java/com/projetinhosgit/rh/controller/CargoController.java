package com.projetinhosgit.rh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetinhosgit.rh.model.Cargo;
import com.projetinhosgit.rh.model.Funcionario;
import com.projetinhosgit.rh.service.CargoService;

@RestController
@RequestMapping("api/cargos")
public class CargoController {
	private final CargoService cargoService;
	
	public CargoController(CargoService cargoService) {
		this.cargoService = cargoService;
	}
	
	@GetMapping
	public ResponseEntity<List<Cargo>> listarTodos() {
	    List<Cargo> cargos = cargoService.listarTodosCargos();
	    return ResponseEntity.ok(cargos);
	}

	
	 //retornar um cargo por id
	 @GetMapping("/{id}")
	 public ResponseEntity<Cargo> buscarPorId(@PathVariable Long id) {
	        Optional<Cargo> cargo = cargoService.buscarPorId(id);
	        return cargo.map(ResponseEntity::ok)
	        		.orElse(ResponseEntity.notFound().build());
	    }
	 
	 //criar novo cargo
	 @PostMapping
	 public ResponseEntity<Cargo> salvarCargo(@RequestBody Cargo cargo){
		 Cargo novoCargo = cargoService.salvarCargo(cargo);
		 return ResponseEntity.ok(cargo);
	 }
	 
	 //atualiza um novo cargo
	 @PutMapping("/{id}")
	 public ResponseEntity<?> atualizarCargo(@PathVariable Long id, @RequestBody Cargo cargo){
	     try {
	         Cargo cargoAtualizado = cargoService.atulizarCargo(id, cargo);
	         return ResponseEntity.ok(cargoAtualizado);
	     } catch (IllegalArgumentException e) {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cargo não encontrado");
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado");
	     }
	 }

	  // Deletar cargo por ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deletarCargo(@PathVariable Long id) {
	        String mensagem = cargoService.deletarCargo(id);
	        if (mensagem.contains("não encontrado")) {
	            return ResponseEntity.notFound().build();
	        } else {
	            return ResponseEntity.ok(mensagem);
	        }
	    }

	
	

}
