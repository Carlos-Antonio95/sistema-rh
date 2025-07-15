package com.projetinhosgit.rh.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projetinhosgit.rh.model.Cargo;
import com.projetinhosgit.rh.model.Funcionario;
import com.projetinhosgit.rh.repository.CargoRepository;
import com.projetinhosgit.rh.repository.FuncionarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FuncionarioService {
	
	private  FuncionarioRepository funcionarioRepository;
	private CargoRepository cargoRepository;
	

    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
    }

    //Retorna a lista de todos os funcionarios
    public List<Funcionario> listarTodosFuncionarios(){
    	return funcionarioRepository.findAll();
    }
    
    //Retorna um funcionario por id
    public Optional<Funcionario> buscarPorId(Long id) {
    	return funcionarioRepository.findById(id);
    }
    
    //Retorna um funcionario por cpf
    public Optional<Funcionario> buscarPorCpf(String cpf){
    	return funcionarioRepository.findByCpf(cpf);
    }

    //Método para salvar funcionario
    public Funcionario salvarFuncionario(Funcionario funcionario) {
    	return funcionarioRepository.save(funcionario);
    }
    
    //Deleta  funcionario por id
    public String deletarPorId(Long id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return "Funcionário com ID " + id + " foi deletado com sucesso.";
        } else {
            return "Funcionário com ID " + id + " não encontrado.";
        }
    }
    
    //Atualizar email e nome do funcionario
    public Funcionario atualizarFuncionario(Long id, Funcionario funcionarioAtualizado) {
    	Optional<Funcionario> funcionarioExistenteOptional = funcionarioRepository.findById(id);
    	
    	if (funcionarioExistenteOptional.isPresent()) {
			Funcionario funcionarioExistente = funcionarioExistenteOptional.get();
			funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());
			funcionarioExistente.setNome(funcionarioAtualizado.getNome());
			return funcionarioRepository.save(funcionarioExistente);
		} else {
			throw new IllegalArgumentException("Funcionario não encontrado");
		}
    }
    
    //Atualizar cargo do funcionario
    public Funcionario atualizarCargo(Long id, Long idCargoAnterior, Long idCargoNovo) {
        Optional<Funcionario> funcionarioExistenteOptional = funcionarioRepository.findById(id);
        Optional<Cargo> cargoAnteriorExistenteOptional = cargoRepository.findById(idCargoAnterior);
        Optional<Cargo> cargoNovoExistenteOptional = cargoRepository.findById(idCargoNovo);

        if (funcionarioExistenteOptional.isPresent() &&
            cargoAnteriorExistenteOptional.isPresent() &&
            cargoNovoExistenteOptional.isPresent()) {
            
            Funcionario funcionarioExistente = funcionarioExistenteOptional.get();
            Cargo cargoAtual = funcionarioExistente.getCargo();

            // Verifica se o funcionário está no cargoAnterior antes de atualizar
            if (!cargoAtual.getId().equals(idCargoAnterior)) {
                throw new IllegalArgumentException("Funcionário não está no cargo anterior informado.");
            }

            // Atualiza para o novo cargo
            funcionarioExistente.setCargo(cargoNovoExistenteOptional.get());
            return funcionarioRepository.save(funcionarioExistente);
        }

        throw new EntityNotFoundException("Funcionário ou cargos não encontrados.");
    }

    //Atualizar bônus funcionário
    public String atualizarBonusFuncionario(Long id, BigDecimal salarioAtualizado) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);

        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();
            funcionario.setSalario(salarioAtualizado);
            funcionarioRepository.save(funcionario);
            return "Salário do funcionário " + funcionario.getNome() + " atualizado para R$ " + funcionario.getSalario();
        } else {
            throw new IllegalArgumentException("Funcionário não encontrado");
        }
    }

}
