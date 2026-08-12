/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.repository.LojaRepository;
import com.logisticab2bapi.logistica_api.service.LojaService;
import com.logisticab2bapi.logistica_api.service.TokenService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @Autowired private LojaService lojaService;
    @Autowired private LojaRepository lojaRepo;

    private Usuario getUsuario(String auth){
        String token = auth.replace("Bearer ", "");
        return tokenService.extrairClaim(token);
    }
    
    @PostMapping
    public Loja criar(@RequestHeader("Authorization") String auth, @RequestBody Loja loja){
        String token = auth.replace("Bearer ", "");
        Usuario logado = tokenService.extrairClaim(token);
        loja.setIdUsuario(logado.getId());
        loja.setAtivo(true);
        return lojaRepo.save(loja);
    }
    
    @GetMapping
    public List<Loja> listar(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        
        Usuario logado = tokenService.extrairClaim(token);
        if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN){
            return lojaRepo.findAll();
        }
        
        return lojaRepo.findByIdUsuario(logado.getId());
    }
    
    @GetMapping("/ativas")
    public List<Loja> listarAtivas(@RequestHeader("Authorization") String auth){
        return lojaService.listarAtivas(getUsuario(auth));
    }
    
    @GetMapping("/desativadas")
    public List<Loja> listarDesativadas(@RequestHeader("Authorization") String auth){
        Usuario logado = getUsuario(auth);
        if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN) return lojaRepo.findByAtivoFalse();
        return List.of();
    }

     @PutMapping("/{id}")
    public Loja editar(@PathVariable Long id, @RequestHeader("Authorization") String auth, @RequestBody Loja loja){
        return lojaService.atualizar(id, loja, getUsuario(auth));
    }
    
    @PutMapping("/{id}/arquivar")
    public Loja arquivar(@PathVariable Long id, @RequestHeader("Authorization") String auth){
        return lojaService.arquivar(id, getUsuario(auth));
    }
    
    
    
}
