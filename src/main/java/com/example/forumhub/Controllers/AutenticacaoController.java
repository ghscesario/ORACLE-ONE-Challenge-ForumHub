package com.example.forumhub.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forumhub.DTO.DadosAutenticacao;
import com.example.forumhub.DTO.DadosTokenJWT;
import com.example.forumhub.Models.Usuarios;
import com.example.forumhub.Security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacao dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(token);

        var tokenJWT = tokenService.gerarToken((Usuarios) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); 
    }

}
