
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

import com.logisticab2bapi.logistica_api.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author BEATRICE
 */
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public SecretKey getKeySign() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    
    public String gerarToken(Usuario user) {
        if(user.getId() == null || user.getEmail().equals("") || user.getPerfilRole() == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Um ou mais campos faltantes");
        }

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getPerfilRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1h
                .signWith(this.getKeySign())
                .compact();
    }

    
    public Usuario extrairClaim(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(this.getKeySign())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Usuario u = new Usuario();
        u.setId(claims.get("id", Long.class));
        u.setEmail(claims.get("email", String.class));
        u.setPerfilRole(Usuario.PerfilRole.valueOf(claims.get("role", String.class)));
        return u;
    }
    
      public boolean validarToken(String token) {
        try {
            // Cria um parser JWT com a chave secreta para validação
            Jwts.parser()
                    .setSigningKey(getKeySign())
                    .build()
                    // Analisa e valida o token (lança exceção se inválido ou expirado)
                    .parseClaimsJws(token);
            // Se chegou aqui, o token é válido
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Se qualquer exceção ocorrer, o token é inválido ou expirou
            return false;
        }
    }
    
    
}
