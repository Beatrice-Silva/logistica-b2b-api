/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pacotes")
public class Pacote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoRastreio;

     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_loja")
    private Loja loja;
     
    private String enderecoDestino;

    @Enumerated(EnumType.STRING)
    private StatusAtual statusAtual;
    
    @Column(name = "email_destinatario")
    private String emailDestinatario;
    
    private String otpCodigo;

    private LocalDateTime otpExpira;

    private String descObserv;

    //opcoes limitadas de status
    public enum StatusAtual {
        CRIADO, //operador cria
        COLETADO, //operador coleta
        EM_TRANSITO, //com entregador
        ENTREGUE, //Com OTP valido
        DEVOLVIDO, // Com a justificacao de emprevisto na entrega
        //cancelamento -> EM_TRANSITO -> COLETADO
        ARQUIVADO // Para exclusao final
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public String getEnderecoDestino() {
        return enderecoDestino;
    }

    public void setEnderecoDestino(String enderecoDestino) {
        this.enderecoDestino = enderecoDestino;
    }

    public StatusAtual getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(StatusAtual statusAtual) {
        this.statusAtual = statusAtual;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
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

    public String getDescObserv() {
        return descObserv;
    }

    public void setDescObserv(String descObserv) {
        this.descObserv = descObserv;
    }

    

    
}
