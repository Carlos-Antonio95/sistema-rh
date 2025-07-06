package com.projetinhosgit.rh.model;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;
import jakarta.validation.constraints.Size;

import jakarta.persistence.*; // Importe do pacote jakarta.persistence para JPA
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(unique = true, length = 50)
    private String login; 

    @NotNull
    @Size(max = 255)
    @Column(length = 255)
    private String senhaHash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", referencedColumnName = "id")
    private Funcionario funcionario;
}
