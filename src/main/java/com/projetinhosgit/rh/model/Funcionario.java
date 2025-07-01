package com.projetinhosgit.rh.model;

import java.time.LocalDate;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



//Depedencias JPA Entity diz que é uma entidade para ser criada no banco de dados
@Setter
@Entity
//getters e setters ja estão criados
@Getter
//Contrutores com parametros e sem parametros
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

	//id e primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @BatchSize(size = 100)
    private String nome;

 
    @NotNull
    @Column(unique = true, length = 100)
    private String email;

    @NotNull
    @BatchSize(size = 14)
    @Column(unique = true, length = 14)
    private String cpf;

 
    private LocalDate dataNascimento;

    private LocalDate dataAdmissao;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "salario_id", nullable = false)
    private Salario salario;
   



}