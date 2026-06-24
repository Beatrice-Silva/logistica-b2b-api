/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.OtpTentativaDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author BEATRICE
 */
public interface OtpTentativaRepository extends JpaRepository<OtpTentativaDTO, Long> {
    OtpTentativaDTO findByCodigoLon(String codigoLon);
    
    Optional<OtpTentativaDTO> findByIdPacote(Long idPacote);
}
