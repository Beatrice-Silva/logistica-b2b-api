/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import ch.qos.logback.core.model.Model;
import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.repository.LojaRepository;
import com.logisticab2bapi.logistica_api.service.LojaService;
import com.logisticab2bapi.logistica_api.service.TokenService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
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

    @PostMapping
    public Loja criar(@RequestHeader("Authorization") String auth, @RequestBody Loja loja){
        String token = auth.replace("Bearer ", "");
        Usuario logado = tokenService.extrairClaim(token);
        loja.setIdUsuario(logado.getId());
        loja.setAtivo(true);
        return lojaRepo.save(loja);
    }
    
    
    @PutMapping("/{id}")
    public Loja editarLoja(@PathVariable Long id,@RequestHeader("Authorization") String auth, @RequestBody Loja loja){
        Usuario logado = tokenService.extrairClaim(auth.replace("Bearer",""));
        
          if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN){
            return lojaRepo.findAllById(ids);
        }
          
        return lojaRepo.findByIdUsuario(logado.getId(IdUsuario));      
        
        return lojaRepo.save(auth);
    }

    
    @GetMapping
    public List<Loja> listar(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        
        Usuario logado = tokenService.extrairClaim(token);
        if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN){
            return lojaRepo.findAll();
        }
        //return lojaRepo.findByAtivoTrue();
        return lojaRepo.findByIdUsuario(logado.getId());
    }

    @GetMapping("/lojas/desativas")
    public List<Loja> listarDesativadas(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        
        Usuario logado = tokenService.extrairClaim(token);
        if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN){
            return lojaRepo.findByAtivoTrue();
        }
        
        
    }

    @PutMapping("/{id}")
    public Loja LojaPorId(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        
        Usuario logado = tokenService.extrairClaim(auth.replace("Bearer",""));
        if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN){
            return lojaRepo.getById(Long.MIN_VALUE);
        }
        
        return lojaRepo.findByIdUsuario(logado.getId());
    
    }
    
    @GetMapping("/loja/{id}")
    public List<Loja> listarPacotesPorLoja() {
        String token = (String) session.getAttribute("token");
        
        Usuario logado = tokenService.extrairClaim(token);
        if(logado.getPerfilRole() == Usuario.PerfilRole.ADMIN){
            return lojaRepo.findByAtivoTrue();
        }
        
        return lojaService.listarPacotesPorLoja(Long.MIN_VALUE, logado); 
    }
    
    
    @PutMapping("/{id}/arquivar")
    public Loja arquivarLoja(Loja id, @RequestHeader("Authorization") String auth){
        
        Usuario usuarioLogado = tokenService.extrairClaim(auth.replace("Bearer",""));
        return lojaService.arquivar(Long.MIN_VALUE, usuarioLogado);
    }
    
    
    
}
