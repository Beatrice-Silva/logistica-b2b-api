/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.service.TokenService;
import com.logisticab2bapi.logistica_api.service.UsuarioService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired private UsuarioService usuarioService;
    @Autowired private TokenService tokenServicee;

    @PostMapping("/login")
    //AuthResponseDTo da erro pela classe nao criada ainda
    public ResponseEntity<AuthResponseDTO> login(@RequestBody Map<String,String> login){
        
        Usuario u = usuarioService.validarLogin(login.get("email"), login.get("senha"));
        
        String token = tokenService.gerarToken(u);
        return ResponseEntity.ok(new AuthResponseDTO(token, u.getPerfilRole().name(), u.getEmail()));
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Usuario user){
        usuarioService.register(user);
        return ResponseEntity.ok("Cadastro feito com sucesso");
    }
}
     

    
    
    
    
  
