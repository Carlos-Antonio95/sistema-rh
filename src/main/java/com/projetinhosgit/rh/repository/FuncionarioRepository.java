package com.projetinhosgit.rh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetinhosgit.rh.model.Funcionario;

public  interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
