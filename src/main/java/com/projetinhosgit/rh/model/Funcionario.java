package com.projetinhosgit.rh.model;

import java.math.BigDecimal;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100)
    private String nome;

    @NotBlank
    @Size(max = 100)
    @Column(unique = true, length = 100)
    private String email;

    @NotBlank
    @Size(max = 14)
    @Column(unique = true, length = 14)
    private String cpf;

    private LocalDate dataNascimento;
    private LocalDate dataAdmissao;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    private BigDecimal salario;
}




