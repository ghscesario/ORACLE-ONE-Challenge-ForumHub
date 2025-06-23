package com.example.forumhub.Security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.forumhub.Models.Usuarios;

@Service
public class TokenService {

    private static final String SECRET = "12345678";
    
    public String gerarToken(Usuarios usuario){
        
        try {
        var algoritimo = Algorithm.HMAC256(SECRET);
        return JWT.create()
            .withIssuer("test")
            .withSubject(usuario.getLogin())
            .withExpiresAt(dataExpiracao())
            .sign(algoritimo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT");
        }

    }

    public String getSubject(String tokenJWT){
        try {
            var algoritimo = Algorithm.HMAC256(SECRET);
            return JWT.require(algoritimo)
                .withIssuer("test")
                .build()
                .verify(tokenJWT)
                .getSubject();
            } catch (JWTVerificationException exception){
                throw new RuntimeException("Token JWT inválido ou expirado: " +tokenJWT);
            }
        }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
