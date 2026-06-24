/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.logisticab2bapi.logistica_api.repository;

import com.logisticab2bapi.logistica_api.model.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author BEATRICE
 */
public interface UsuarioRepository extends JpaRepository<UsuarioDTO, Long> {
    UsuarioDTO findByEmail(String email);
    UsuarioDTO findByEmailAndSenha(String email, String senha);
}
