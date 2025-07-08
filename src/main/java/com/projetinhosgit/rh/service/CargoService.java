package com.projetinhosgit.rh.service;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.projetinhosgit.rh.model.Cargo;
import com.projetinhosgit.rh.repository.CargoRepository;

@Service
public class CargoService {
	private CargoRepository cargoRepository;
	
	
	public CargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	//métodos CRUD de cargo
	
	//método para listar todos os cargos
	public List<Cargo> listarTodosCargos(){
		return cargoRepository.findAll();
	}
	
	//método par retrnar um cargo por id
	public Optional<Cargo> buscarPorId(Long id){
		return cargoRepository.findById(id);
	}
	
	//Método adicionar cargo
	
	public Cargo salvarCargo(Cargo cargo) {
		return cargoRepository.save(cargo);
	}
	
	//Método atualizar cargo
	public String atulizarCargo(Long id, Cargo cargoAtualizado) {
		Optional<Cargo> cargoExistenteOptional = cargoRepository.findById(id);
		if (cargoExistenteOptional.isPresent()) {
			Cargo cargoExistente = cargoExistenteOptional.get();
			cargoExistente.setSetor(cargoAtualizado.getSetor());
			cargoExistente.setNome(cargoAtualizado.getNome());
			cargoRepository.save(cargoExistente);
			return "Cargo atualizado para: "+cargoExistente.getNome()+" , setor atualizado para "+cargoExistente.getSetor();
		}else {
			  throw new IllegalArgumentException("Cargo não encontrado");
		}
		
	}
	
	//método para excluir um cargo
	public String deletarCargo(Long id) {
		if (cargoRepository.existsById(id)) {
			cargoRepository.deleteById(id);
			return "Cargo com id: "+id+" deletado com sucesso.";
		} else {
			return "Cargo não encontrado ";
		}
	}
}
