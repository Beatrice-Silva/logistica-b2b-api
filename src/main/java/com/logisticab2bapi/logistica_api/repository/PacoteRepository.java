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
@Repository //repo ->
public interface PacoteRepository extends JpaRepository<Pacote, Long> {
    
    //consulta publica do destinatario
    Optional<Pacote> findByCodigoRastreio(String codigoRastreio);
    
    //Contagem de quantos em cada status no dashboard
    long countByStatusAtual(StatusAtual statusAtual);

    //Lista por id especifico
    List<Pacote> findByIdLoja(Long idLoja);
    
//pacote com loja 
    @Query("SELECT p FROM Pacote p JOIN FETCH p.loja")
    List<Pacote> findAllComLoja();
    
    //SELECT p.codigo_rastreio, l.nome_estabelecimento
    //FROM pacotes p INNER JOIN lojas l ON p.id_loja = l.id;
    
    
    @Query("SELECT p.status_atual as status,\n" +
" COUNT(p) as total FROM Pacotes  p GROUP BY p.status_atual;")
    List<Object[]> contarPorStatus();
    
    
    
}