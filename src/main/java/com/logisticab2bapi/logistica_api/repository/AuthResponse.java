/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.Usuario;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author BEATRICE
 */
@Repository
public interface AuthResponse {
    Optional<Usuario> findByEmail(String email);
    
}
