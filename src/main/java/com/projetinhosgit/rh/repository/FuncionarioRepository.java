package com.projetinhosgit.rh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetinhosgit.rh.model.Funcionario;

@Repository
public  interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	   // Busca funcionário pelo CPF
    Optional<Funcionario> findByCpf(String cpf);
	
}
