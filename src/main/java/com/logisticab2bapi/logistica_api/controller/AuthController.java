/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.AuthResponseDTO;
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
public class AuthController {
    @Autowired private UsuarioService service;

    @PostMapping("/registrar")
    public String registrar(@RequestBody Usuario user) {
        service.registrar(user);
        return "Cadastro Feito com sucesso!";
    }

    @PostMapping("/logar")
    public String login(@RequestBody Map<String,String> user) {
        return service.login(user.get("email"), user.get("senha"));
    }
}
     

    
    
    
    
  
