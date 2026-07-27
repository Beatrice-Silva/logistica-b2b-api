/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.service.PacoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BEATRICE
 */ 
@RestController
@RequestMapping("/api/pacotes")
@CrossOrigin(origins = "*")
public class PacoteController {
    @Autowired private PacoteService service;

    @PostMapping
    public Pacote criar(@RequestBody Pacote pacote){ return service.criar(pacote); }

    @GetMapping("/{codigo}")
    public Pacote rastrear(@PathVariable String codigo){ return service.buscarPorCodigo(codigo); }

    @PutMapping("/{id}/status")
    public Pacote status(@PathVariable Long id, @RequestParam String novo, @RequestParam(required = false) String otp, @RequestParam String perfil){
        return service.atualizar(id, novo, otp, perfil);
    }
}