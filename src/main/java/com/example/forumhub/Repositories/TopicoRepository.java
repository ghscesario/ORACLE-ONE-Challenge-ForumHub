package com.example.forumhub.Repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forumhub.Models.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

    public Optional<Topico> findByTitulo (String titulo);

    Page<Topico> findByEstadoTopicoTrue(Pageable pageable);
    
}
