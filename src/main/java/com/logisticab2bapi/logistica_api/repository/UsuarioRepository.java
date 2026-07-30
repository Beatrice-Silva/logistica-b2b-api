/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 *
 * @author BEATRICE
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findByRole(enum PerfilRole = "ADMIN");
    
    List<Usuario> findByRole(enum PerfilRole = "ENTREGADOR");
    
    List<Usuario> findByRole(enum PerfilRole = "OPERADOR");
    
    boolean existsByEmail(String email);
    }
