/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;
import java.util.Optional;
import com.logisticab2bapi.logistica_api.model.OtpTentativaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Aluno
 */
public interface OtpTentativaRepository extends JpaRepository<OtpTentativaDTO, Long> {
    OtpTentativaDTO findByCodigoLon(String codigoLon);

}

/*
CREATE TABLE otp_tentativa (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_pacote BIGINT,
  tentativas INT DEFAULT 0,
  bloqueio_ate DATETIME
);

*/

