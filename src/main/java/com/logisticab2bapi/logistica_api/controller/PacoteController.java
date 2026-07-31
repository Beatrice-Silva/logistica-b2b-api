/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.repository.PacoteRepository;
import com.logisticab2bapi.logistica_api.service.PacoteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BEATRICE
 */ 
@RestController
@RequestMapping("/api/pacotes")
public class PacoteController {
    
    @Autowired private PacoteService service;

    @PostMapping
    public String criar(@RequestHeader("Authorization") String auth, @RequestBody Pacote pacote){
        String token = auth.replace("Bearer ", "");
        //service.criar(pacote, token);
        return "Pacote cadastrado!";
    }

    @GetMapping("/{codigo}")
    public Pacote rastrear(@PathVariable String codigo){
        return service.buscarPorCodigo(codigo); 
    }

    @PutMapping("/{id}/status")
    public String status(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestParam String novo, @RequestParam(required = false) String otp){
        String token = auth.replace("Bearer ", "");
        service.atualizar(id, novo, otp, token);
        return "Status atualizado!";
    }
    
    @GetMapping
    public List<Pacote> listar(@RequestHeader("Authorization") String auth){ 
        String token = auth.replace("Bearer ", "");
        //return service.listarTodos(token);
        return null;
    }
    
}