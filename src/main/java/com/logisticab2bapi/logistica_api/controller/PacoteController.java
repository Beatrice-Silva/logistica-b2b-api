/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.controller;

import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.LojaCountDTO;
import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.repository.PacoteRepository;

import com.logisticab2bapi.logistica_api.service.PacoteService;
import com.logisticab2bapi.logistica_api.service.TokenService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BEATRICE
 */ 
//Api testes
@RestController
@RequestMapping("/api/pacotes")
public class PacoteController {
    
    @Autowired 
    private PacoteService service;
    @Autowired 
    private PacoteRepository repo;
    @Autowired 
    private TokenService tokenService;
    

    @PostMapping("/registrar")
    public String criarPacote(
            @RequestHeader("Authorization") String auth, 
            @RequestBody Pacote pacote)
    {
        
        String token = auth.replace("Bearer ", "");
        Usuario usuarioLogado = tokenService.extrairClaim(token);
        service.novoPacote(pacote, usuarioLogado);
        return "Pacote cadastrado com sucesso!";
    }
    
    @GetMapping("/listar")
    public List<Pacote> listar(@RequestHeader("Authorization") String auth){ 
        String token = auth.replace("Bearer ", "");
        return service.listarPacote(token);
    }
    
    @GetMapping("/estatisticas")
    public Map<String, Long> estatisticas(){
        Map<String, Long> map = new HashMap<>();
        for(Object[] row : repo.contarPorStatus()){
            map.put(row[0].toString(), ((Number)row[1]).longValue());
        }
        return map;
    }
    
    @GetMapping("/por-loja")
    public List<LojaCountDTO> porLoja(){
        List<LojaCountDTO> lista = new ArrayList<>();
        for(Object[] row : repo.contarPorLoja()){
            lista.add(new LojaCountDTO((String)row[0], ((Number)row[1]).longValue()));
        }
        return lista;
    }
    
    @PostMapping("/{id}/status")
    public String status(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestParam String novo, @RequestParam(required = false) String otp){
        String token = auth.replace("Bearer ", "");
        service.atualizar(id, novo, otp, token);
        return "Status atualizado!";
    }
    
    @GetMapping("/codigo/{codigo}")
    public Pacote rastrear(@PathVariable String codigo){
        return service.buscarPorCodigo(codigo); 
    }
    @GetMapping("/public/rastreio/{codigo}")
    public Pacote rastreioPublico(@PathVariable String codigo){
    return repo.findByCodigoRastreio(codigo).orElseThrow();
    }
    
    @GetMapping("/arquivados")
    public List<Pacote> listarArquivados(){
        return repo.contarPorStatus(status);
    }
    
}