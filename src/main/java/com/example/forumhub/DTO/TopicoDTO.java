package com.example.forumhub.DTO;

import java.time.LocalDate;

import com.example.forumhub.Models.Topico;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of="id")
@AllArgsConstructor
@NoArgsConstructor
public class TopicoDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDate dataCriacao;
    private Boolean estadoTopico;
    private String autor;
    private String curso;
    
    public TopicoDTO (Topico topico){
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.estadoTopico = topico.getEstadoTopico();
        this.autor = topico.getAutor();
        this.curso = topico.getCurso();
    }

}
