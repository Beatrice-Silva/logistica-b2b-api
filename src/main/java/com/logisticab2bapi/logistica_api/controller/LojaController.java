/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.repository.LojaRepository;
import com.logisticab2bapi.logistica_api.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BEATRICE
 */
@RestController
@RequestMapping("/api/lojas")
@CrossOrigin
public class LojaController{
    
    @Autowired private TokenService tokenService;
    
    @Autowired private LojaRepository repo;

    @PostMapping
    public Loja criar(@RequestHeader("Authorization") String auth, @RequestBody Loja loja){
        String token = auth.replace("Bearer ", "");
        Usuario logado = tokenService.extrairClaim(token);
        loja.setIdUsuario(logado.getId());
        loja.setAtivo(true);
        return repo.save(loja);
    }

    @GetMapping
    public List<Loja> listar(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        Usuario logado = tokenService.extrairClaim(token);
        if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN){
            return repo.findAll();
        }
        return repo.findByIdUsuario(logado.getId());
    }

    @GetMapping("/ativas")
    public List<Loja> listarAtivas(){
        return repo.findByAtivoTrue(true);
    }
    
}
