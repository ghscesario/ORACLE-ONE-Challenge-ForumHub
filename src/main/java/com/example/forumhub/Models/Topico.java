package com.example.forumhub.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A mensagem é obrigatória")
    private String mensagem;

    @NotNull(message = "A data de criação é obrigatória")
    private LocalDate dataCriacao;

    @NotNull
    private Boolean estadoTopico;

    @NotBlank(message = "O autor é obrigatório")
    private String autor;

    @NotBlank(message = "O curso é obrigatório")
    private String curso;
    
}
