package com.example.forumhub.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.forumhub.Models.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long>{

	public UserDetails findByLogin(String login);

}
