/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author BEATRICE
 */
@Entity 
@Table(name="otp_tentativas")
public class OtpTentativaDTO {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    
    private Long id;
    @Column(name="id_pacote") private Long idPacote;
    
    private Integer tentativas = 0;
    @Column(name="bloqueio_ate") private LocalDateTime bloqueioAte;   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(Long idPacote) {
        this.idPacote = idPacote;
    }

    public Integer getTentativas() {
        return tentativas;
    }

    public void setTentativas(Integer tentativas) {
        this.tentativas = tentativas;
    }

    public LocalDateTime getBloqueioAte() {
        return bloqueioAte;
    }

    public void setBloqueioAte(LocalDateTime bloqueioAte) {
        this.bloqueioAte = bloqueioAte;
    }



}


