/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.model.Usuario.PerfilRole;
import com.logisticab2bapi.logistica_api.repository.LojaRepository;
import java.time.Year;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class LojaService {
    
        @Autowired
        private LojaRepository lojaRepo;
    
    
        public Loja novaLoja(Loja l, Usuario usuarioLogado){
        
            if(usuarioLogado.getPerfilRole()!= PerfilRole.OPERADOR && usuarioLogado.getPerfilRole()!= PerfilRole.ADMIN){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: apenas Operadores ou administradores");
        }
        if(lojaRepo.findByCnpj(l.getCnpj()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ jà cadastrado!");
        }
        
        l.setIdUsuario(usuarioLogado.getId());
        l.setAtivo(true);
        return lojaRepo.save(l);
    }   
    
    public List<Loja> listar(Usuario usuarioLogado){
        if(usuarioLogado.getPerfilRole() == Usuario.PerfilRole.ADMIN) return lojaRepo.findAll();
        return lojaRepo.findByIdUsuario(usuarioLogado.getId());
    }

    public List<Loja> listarAtivas(Usuario usuarioLogado){
        if(usuarioLogado.getPerfilRole() == Usuario.PerfilRole.ADMIN) 
            return lojaRepo.findByAtivoTrue();
        return lojaRepo.findByIdUsuarioAndAtivoTrue(usuarioLogado.getId());
    }
    
    
        public Loja atualizar(Long id, Loja nova, Usuario logado){
        Loja atual = lojaRepo.findById(id).orElseThrow();

        if(logado.getPerfilRole() != Usuario.PerfilRole.ADMIN 
                && !atual.getIdUsuario().equals(logado.getId())){
             throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        atual.setNomeEstabelecimento(nova.getNomeEstabelecimento());
        atual.setContatoEmail(nova.getContatoEmail());
        atual.setCidade(nova.getCidade());
        atual.setEndereco(nova.getEndereco());
        return lojaRepo.save(atual);
        }
  
   
    
    public Loja arquivar(Long id, Usuario usuarioLogado){
        Loja l = lojaRepo.findById(id).orElseThrow();
        if(
                usuarioLogado.getPerfilRole() 
                != Usuario.PerfilRole.ADMIN 
                && !l.getIdUsuario().equals(usuarioLogado.getId())){
            
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        
        l.setAtivo(false);
        return lojaRepo.save(l);
    }
    
    
   
    
}
