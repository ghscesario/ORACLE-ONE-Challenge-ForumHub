package com.example.forumhub.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forumhub.Models.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
    
}
