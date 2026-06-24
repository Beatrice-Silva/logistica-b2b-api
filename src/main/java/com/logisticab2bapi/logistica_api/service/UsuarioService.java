/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

import com.logisticab2bapi.logistica_api.model.UsuarioDTO;
import com.logisticab2bapi.logistica_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author BEATRICE
 */

@Service
public class UsuarioService {
    
   @Autowired
    private UsuarioRepository repository;
    
    public UsuarioDTO register(UsuarioDTO user){
        if(user.getNome() == null || user.getNome().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome não preenchido");
        }
        if(user.getEmail() == null || user.getEmail().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não preenchido");
        }
        if(user.getSenha() == null || user.getSenha().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha não preenchida");
        }
        if(user.getPerfilrole() == null || user.getPerfilrole().isBlank()){
            user.setPerfilrole("operador"); // padrão
        }
        if(repository.findByEmail(user.getEmail()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        return repository.save(user);
    }
    
    public UsuarioDTO logar(String email, String senha){
        if(email == null || email.isBlank() || senha == null || senha.isBlank()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email e senha obrigatórios");
        }
        UsuarioDTO user = repository.findByEmailAndSenha(email, senha);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha incorretos");
        }
        user.setSenha(null);
        return user;
    }      
    }