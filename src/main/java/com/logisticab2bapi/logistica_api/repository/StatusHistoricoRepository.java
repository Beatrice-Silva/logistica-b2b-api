/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.StatusHistoricoDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author BEATRICE
 */
public interface StatusHistoricoRepository extends JpaRepository<StatusHistoricoDTO, Long> {
    //List<StatusHistoricoDTO> findByCodigoLonOrderByDataHoraDesc(String codigoLon);
}
