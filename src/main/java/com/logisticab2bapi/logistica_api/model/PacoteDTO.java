/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pacotes")
@Access(AccessType.FIELD)
public class PacoteDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_lon", unique = true, nullable = false)
    private String codigoLon;

    @Column(name = "otp_codigo")
    private String otpCodigo;

    @Column(name = "otp_expira")
    private LocalDateTime otpExpira;

    @Column(name = "id_loja")
    private Long idLoja;

    private String endereco;

    @Column(name = "status_atual")
    private String statusAtual;

    @Column(name = "desc_observ")
    private String descObserv;

    private Double peso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoLon() {
        return codigoLon;
    }

    public void setCodigoLon(String codigoLon) {
        this.codigoLon = codigoLon;
    }

    public String getOtpCodigo() {
        return otpCodigo;
    }

    public void setOtpCodigo(String otpCodigo) {
        this.otpCodigo = otpCodigo;
    }

    public LocalDateTime getOtpExpira() {
        return otpExpira;
    }

    public void setOtpExpira(LocalDateTime otpExpira) {
        this.otpExpira = otpExpira;
    }

    public Long getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(Long idLoja) {
        this.idLoja = idLoja;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public String getDescObserv() {
        return descObserv;
    }

    public void setDescObserv(String descObserv) {
        this.descObserv = descObserv;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

   
    
}