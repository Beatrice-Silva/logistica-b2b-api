/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.Loja;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author BEATRICE
 */
@Repository //Loja -> loja origem
public interface LojaRepository extends JpaRepository<Loja, Long> {
    
    //Nao deixar cpnj ser duplicado quando cadastrado
    Optional<Loja> findByCnpj(String cnpj);
    
    List<Loja> findByAtivoTrue();
    //Listar lojas apenas ativas para quando criar remessa
    
    List<Loja> findByIdUsuario(Long idUsuario);
    
    
    
    
    
    
    
    
    
}
    

