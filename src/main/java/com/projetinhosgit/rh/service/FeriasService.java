package com.projetinhosgit.rh.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.projetinhosgit.rh.repository.FeriasRepository;
import com.projetinhosgit.rh.model.Ferias;
@Service
public class FeriasService {
	private final FeriasRepository feriasRepository;
	
	public FeriasService(FeriasRepository feriasRepository) {
		this.feriasRepository = feriasRepository;
	}
	
	private void validarFerias(Ferias ferias) {
	    LocalDate inicio = ferias.getDataInicio();
	    LocalDate fim = ferias.getDataFim();

	    if (inicio == null || fim == null) {
	        throw new IllegalArgumentException("As datas de início e fim são obrigatórias.");
	    }

	    if (inicio.isAfter(fim)) {
	        throw new IllegalArgumentException("A data de início deve ser antes da data de fim.");
	    }

	    long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio, fim) + 1;
	    if (dias < 5 || dias > 30) {
	        throw new IllegalArgumentException("Período de férias deve ter entre 5 e 30 dias.");
	    }

	    List<Ferias> feriasAtuais = feriasRepository.findByFuncionarioId(ferias.getFuncionario().getId());
	    for (Ferias f : feriasAtuais) {
	        boolean sobrepoe =
	            !(fim.isBefore(f.getDataInicio()) || inicio.isAfter(f.getDataFim()));

	        if (sobrepoe && (ferias.getId() == null || !ferias.getId().equals(f.getId()))) {
	            throw new IllegalArgumentException("Funcionário já possui férias nesse período.");
	        }
	    }
	}


	
	//Listar todas as férias
	public List<Ferias> listarTodasFerias(){
		return feriasRepository.findAll();
		}
	
	//Buscar Férias por ID
	public Optional<Ferias> buscarPorId(Long id){
		return feriasRepository.findById(id);
	}
	
	//Salvar um novo registro de férias
	public Ferias salvarFerias(Ferias ferias) {
	    validarFerias(ferias);
	    return feriasRepository.save(ferias);
	}
	

    // Atualizar um registro de férias existente
	public Ferias atualizarFerias(Long id, Ferias feriasAtualizadas) {
	    if (!feriasRepository.existsById(id)) {
	        throw new IllegalArgumentException("Registro de férias não encontrado.");
	    }

	    feriasAtualizadas.setId(id); // Garantir ID correto para não criar novo
	    validarFerias(feriasAtualizadas);
	    return feriasRepository.save(feriasAtualizadas);
	}

    // Deletar um registro de férias por ID
    public String deletarPorId(Long id) {
        if (feriasRepository.existsById(id)) {
            feriasRepository.deleteById(id);
            return "Registro de férias com ID " + id + " foi deletado com sucesso.";
        } else {
            return "Registro de férias com ID " + id + " não encontrado.";
        }
    }
}
