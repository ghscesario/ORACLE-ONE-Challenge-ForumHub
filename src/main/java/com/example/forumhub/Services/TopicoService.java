package com.example.forumhub.Services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.forumhub.DTO.TopicoDTO;
import com.example.forumhub.Models.Topico;
import com.example.forumhub.Repositories.TopicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TopicoService {
    
@Autowired
private TopicoRepository topicoRepository;


    public TopicoDTO insert(TopicoDTO dto) {
        Topico topico = new Topico();
        topico.setTitulo(dto.getTitulo());
        topico.setMensagem(dto.getMensagem());
        topico.setDataCriacao(LocalDate.now());
        topico.setEstadoTopico(dto.getEstadoTopico());
        topico.setAutor(dto.getAutor());
        topico.setCurso(dto.getCurso());
        topicoRepository.save(topico);
        return new TopicoDTO(topico);
    }

    public Page<TopicoDTO> findAll(Pageable pageable) {
    return topicoRepository.findAll(pageable)
                           .map(TopicoDTO::new);
    }

    public TopicoDTO findById(Long id){
        Topico topico = topicoRepository.findById(id).orElseThrow();
        return new TopicoDTO(topico);
    }

    public TopicoDTO update(Long id, TopicoDTO novo){
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if(optionalTopico.isPresent()){     
            Topico topico = optionalTopico.get();
            topico.setTitulo(novo.getTitulo());
            topico.setMensagem(novo.getMensagem());
            topico.setEstadoTopico(novo.getEstadoTopico());
            topico.setAutor(novo.getAutor());
            topico.setCurso(novo.getCurso());
            topicoRepository.save(topico);
            return new TopicoDTO(topico);
        }
        else{
            throw new EntityNotFoundException("Tópico com id " + id + " não encontrado");
        }
    }

    public void deleteById(Long id){
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if(optionalTopico.isPresent()){
            topicoRepository.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("Tópico não encontrado");
        }
    }

}
