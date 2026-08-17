/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.model.Pacote.StatusAtual;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author BEATRICE
 */
@Repository 
public interface PacoteRepository extends JpaRepository<Pacote, Long> {
    
    List<Pacote> findByLoja_Id(Long id);
    //consulta publica do destinatario
    Optional<Pacote> findByCodigoRastreio(String codigoRastreio);
    
    //Contagem de quantos em cada status no dashboard
    List<Pacote> findByStatusAtual(Pacote.StatusAtual status);
    
    //dashboard 
    @Query(value = "SELECT status_atual, COUNT(*) FROM pacotes GROUP BY status_atual", nativeQuery = true)
    List<Object[]> contarPorStatus();
    
    @Query("SELECT p.loja.id, p.loja.nomeEstabelecimento, COUNT(p) FROM Pacote p GROUP BY p.loja.id, p.loja.nomeEstabelecimento")
    List<Object[]> contarPorLoja();
   
    
}