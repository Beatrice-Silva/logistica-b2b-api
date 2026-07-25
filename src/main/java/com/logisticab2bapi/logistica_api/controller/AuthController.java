/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BEATRICE
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
   
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody Usuario user) {   
        String token = usuarioService.logar(user);
        
        AuthResponseDTO resposta = new AuthResponseDTO();
        resposta.setToken(token);
        
    return ResponseEntity.ok(resposta);
        
    //throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Este edital se encontra `ENCERRADO`! " );
    }
     
}
      
    //Faltam = 
    
    /*
    USUARIOCONTROLLER
    Adicionar ao banco, usuarios como ADMIN
    
    AUDITORIASERVICE
    
    LOGO:
    FRONT END PASTAS E HTMLS
    
    TCC ESCRITO E ORGANIZADO TUDO
    
    DIAGRAMAS FINAIS
    
    SUBIR PRO GITHUB
    
    */
    
    
    
  
