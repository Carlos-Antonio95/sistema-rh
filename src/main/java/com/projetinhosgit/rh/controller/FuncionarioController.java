package com.projetinhosgit.rh.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetinhosgit.rh.dto.FuncionarioDTO;
import com.projetinhosgit.rh.model.Cargo;
import com.projetinhosgit.rh.model.Funcionario;
import com.projetinhosgit.rh.service.CargoService;
import com.projetinhosgit.rh.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final CargoService cargoService;
    

    public FuncionarioController(FuncionarioService funcionarioService, CargoService cargoService) {
        this.funcionarioService = funcionarioService;
        this.cargoService = cargoService;
    }

    // 1. Listar todos os funcionários
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        List<Funcionario> funcionarios = funcionarioService.listarTodosFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    // 2. Buscar funcionário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
        return funcionario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //BUScar funcionario por cpf
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorCpf(cpf);

        if (funcionario.isPresent()) {
            return ResponseEntity.ok(funcionario.get());
        } else {
            return ResponseEntity.status(404).body("Funcionário com CPF " + cpf + " não encontrado.");
        }
    }
    // 3. Criar novo funcionário
    @PostMapping
    public ResponseEntity<?> salvarFuncionario(@RequestBody FuncionarioDTO dto) {
        Optional<Cargo> cargoOptional = cargoService.buscarPorId(dto.getCargoId());
        
        if (cargoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cargo não encontrado");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setDataNascimento(dto.getDataNascimento());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setEmail(dto.getEmail());
        funcionario.setSalario(dto.getSalario());
        funcionario.setCargo(cargoOptional.get());

        Funcionario salvo = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.ok(salvo);
    }



    // 4. Atualizar nome e email do funcionário
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        try {
            Funcionario funcionario = funcionarioService.atualizarFuncionario(id, funcionarioAtualizado);
            return ResponseEntity.ok(funcionario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    


    // 5. Deletar funcionário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFuncionario(@PathVariable Long id) {
        String mensagem = funcionarioService.deletarPorId(id);
        if (mensagem.contains("não encontrado")) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(mensagem);
        }
    }
    
    //Atualizar bôbus funcionário
    @PutMapping("/{id}/bonus")
    public ResponseEntity<String> atualizarBonusFuncionario(@PathVariable Long id, @RequestBody BigDecimal bonus) {
        try {
            String mensagem = funcionarioService.atualizarBonusFuncionario(id, bonus);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Funcionário não encontrado");
        }
    }

}
