package com.projetinhosgit.rh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.projetinhosgit.rh.model.Funcionario;
import com.projetinhosgit.rh.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	private  FuncionarioRepository funcionarioRepository;
	

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    //Retorna a lista de todos os funcionarios
    public List<Funcionario> listarTodosFuncionarios(){
    	return funcionarioRepository.findAll();
    }
    
    //Retorna um funcionario por id
    public Optional<Funcionario> buscarPorId(Long id) {
    	return funcionarioRepository.findById(id);
    }

    //Método para salvar funcionario
    public Funcionario salvarFuncionario(Funcionario funcionario) {
    	return funcionarioRepository.save(funcionario);
    }
    
    //Deleta funcionario
    public String deletarPorId(Long id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return "Funcionário com ID " + id + " foi deletado com sucesso.";
        } else {
            return "Funcionário com ID " + id + " não encontrado.";
        }
    }
    
    //Atualizar email do funcionario
    public Funcionario atualizarFuncionario(Long id, Funcionario funcionarioAtualizado) {
    	Optional<Funcionario> funcionarioExistenteOptional = funcionarioRepository.findById(id);
    	
    	if (funcionarioExistenteOptional.isPresent()) {
			Funcionario funcionarioExistente = funcionarioExistenteOptional.get();
			funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());
			return funcionarioRepository.save(funcionarioExistente);
		} else {
			throw new IllegalArgumentException("Funcionario não encontrado");
		}
    }
}
