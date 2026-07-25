/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author BEATRICE
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    //Retorne opcional para nao quebrar caso nao achar
    Optional<Usuario> findByeEmail(String email);
    
    //verificar se email ja existe quando admin cadastrar
    boolean existsByEmail(String email);
    }
