/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author BEATRICE
 */
@Service
public class TokenService {
    
    //chave utilizada para assinar e validat tokens Jwt
    @Value("${api.security.token.secret}")
    private String secret;
    
    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
        
    }
    
    public String gerarToken() {
        return Jwts.builder()
                .subject("iago.teste@teste.com") //identificador do usuário
                .issuedAt(new Date())// Define quando o token foi criado
                .expiration(new Date(System.currentTimeMillis() + 300000)) //5 min do teste
                .signWith(getSignKey())//assina o token com a chave secreta HMAC-SHA
                .compact();// Converte o token construído para a sua forma compacta (String)
    }
    
    //com usuario do banco
    public boolean validarToken(String token) {
        try {
            Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
            // Se chegou aqui, o token é válido
            return true;
        } catch (Exception e) {
            //se exceção ocorre, o token esta inválido ou expirado
            return false;
        }
    }
    
}
