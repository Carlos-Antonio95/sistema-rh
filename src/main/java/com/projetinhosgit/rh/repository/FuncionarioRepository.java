package com.projetinhosgit.rh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetinhosgit.rh.model.Funcionario;

@Repository
public  interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	
}
