package com.example.forumhub.Services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.forumhub.DTO.TopicoDTO;
import com.example.forumhub.Models.Topico;
import com.example.forumhub.Models.Usuarios;
import com.example.forumhub.Repositories.TopicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TopicoService {
    
@Autowired
private TopicoRepository topicoRepository;


    public TopicoDTO insert(TopicoDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuarios usuario = (Usuarios) authentication.getPrincipal();

        Topico topico = new Topico();
        topico.setTitulo(dto.getTitulo());
        topico.setMensagem(dto.getMensagem());
        topico.setDataCriacao(LocalDate.now());
        topico.setEstadoTopico(true);
        topico.setAutor(usuario.getLogin());
        topico.setCurso(dto.getCurso());
        Optional<Topico> verificacao = topicoRepository.findByTitulo(topico.getTitulo());
        if(verificacao.isPresent()){
            if(verificacao.get().getMensagem().equals(topico.getMensagem())&&verificacao.get().getEstadoTopico().equals(true)){
                throw new RuntimeException("Tópico já existe!");
            }
        }
        topicoRepository.save(topico);
        return new TopicoDTO(topico);
    }

    public Page<TopicoDTO> findAll(Pageable pageable) {
        return topicoRepository.findByEstadoTopicoTrue(pageable)
            .map(TopicoDTO::new);
        //return topicoRepository.findAll(pageable)
            //.map(TopicoDTO::new);
    }

    public TopicoDTO findById(Long id){
        Topico topico = topicoRepository.findById(id).orElseThrow();
        return new TopicoDTO(topico);
    }

    public TopicoDTO update(Long id, TopicoDTO novo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuarios usuario = (Usuarios) authentication.getPrincipal();

        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if(optionalTopico.isPresent() && optionalTopico.get().getAutor().equals(usuario.getLogin())){     
            Topico topico = optionalTopico.get();
            topico.setTitulo(novo.getTitulo());
            topico.setMensagem(novo.getMensagem());
            topico.setCurso(novo.getCurso());
            topicoRepository.save(topico);
            return new TopicoDTO(topico);
        }
        else{
            throw new EntityNotFoundException("Tópico com id " + id + " não encontrado ou o usuário não possui permissão para alterar o tópico!");
        }
    }

    //Exclusão lógica
    public void deleteById(Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Usuarios usuario = (Usuarios) authentication.getPrincipal();

    Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent() && optionalTopico.get().getAutor().equals(usuario.getLogin())) {
            Topico topico = optionalTopico.get();
            topico.setEstadoTopico(false);
            topicoRepository.save(topico);
        } else {
            throw new EntityNotFoundException("Tópico não encontrado ou você não é o autor");
        }
    }

    //Exclusão física no banco (Usar em testes ou situações ocasionais)
    public void deleteByIdPermanent(Long id) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Tópico não encontrado!");
        }
    }

}
