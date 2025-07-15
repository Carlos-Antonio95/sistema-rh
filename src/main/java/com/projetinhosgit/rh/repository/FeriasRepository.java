package com.projetinhosgit.rh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetinhosgit.rh.model.Ferias;

@Repository
public interface FeriasRepository extends JpaRepository <Ferias, Long> {

	List<Ferias> findByFuncionarioId(Long funcionarioId);

}
