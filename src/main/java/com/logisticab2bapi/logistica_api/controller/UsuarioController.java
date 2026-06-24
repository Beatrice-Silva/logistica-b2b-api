/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.UsuarioDTO;
import com.logisticab2bapi.logistica_api.service.AuditoriaService;
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
public class UsuarioController {
    
    @Autowired
    private UsuarioService service; // <-- era AuditoriaService (ERRADO)
    
    @PostMapping("/registrar")
    public UsuarioDTO registrar(@RequestBody UsuarioDTO user){
        return service.register(user);
    }
    
    @PostMapping("/login")
    public UsuarioDTO login(@RequestBody Map<String,String> login) {   
        return service.logar(login.get("email"), login.get("senha"));
    }
}
