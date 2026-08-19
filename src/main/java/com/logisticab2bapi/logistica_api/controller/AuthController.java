/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import ch.qos.logback.core.model.Model;
import com.logisticab2bapi.logistica_api.model.AuthResponseDTO;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.service.UsuarioService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BEATRICE
 */
//Api testes funcionaram
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired private UsuarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario user) {
        Usuario salvo = service.registrar(user);
        return ResponseEntity.ok(Map.of("message", "Cadastro feito com sucesso!", "email", salvo.getEmail()));
    }

    @PostMapping("/logar")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody Map<String,String> req) {
        String token = service.login(req.get("email"), req.get("senha"));
        Usuario u = service.buscarPorEmail(req.get("email")); 
        return ResponseEntity.ok(new AuthResponseDTO(token, u.getPerfilRole().name(), u.getEmail()));
    }
    
    @GetMapping("/administradores")
    public List<Usuario> ListarAdministradores(Model model){
        return service.listarAdmins();
    }
    
    @GetMapping("/operadores")
    public List<Usuario> listarOperadores(Model model){

        return service.listarOperadores();
    }
    
    @GetMapping("/entregadores")
    public List<Usuario> listarEntregadores(Model model){
        return service.listarEntregadores();
    }
    
    @GetMapping("/todos")
    public List<Usuario> listarTodos(Model model){

        return service.listarTodos();
    }
    
    
}
     

    
    
    
    
  
