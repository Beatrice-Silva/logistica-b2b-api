/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity // avisa que essa classe e uma tabela do banco
@Table(name ="usuarios")//nome da tabela no banco
public class Usuario {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String nome;
    private String email;
    
    private String senha;
    
    @Enumerated(EnumType.STRING) //enum salvo como texto
    @Column(name = "perfil_role")
    
    private PerfilRole perfilRole = PerfilRole.OPERADOR;
     @Column(name = "tentativas_otp")
    private Integer tentativasOtp = 0; 

    @Column(name = "bloqueado_ate")
    private LocalDateTime bloqueadoAte;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now(); 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConta status = StatusConta.PENDENTE; 

    public enum StatusConta {
        PENDENTE, 
        ATIVO, 
        BLOQUEADO
    }

    public enum PerfilRole{
        ADMIN,
        OPERADOR, 
        ENTREGADOR
    }

    public StatusConta getStatusConta() {
        return status;
    }

    public void setStatusConta(StatusConta status) {
        this.status = status;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilRole getPerfilRole() {
        return perfilRole;
    }

    public void setPerfilRole(PerfilRole perfilRole) {
        this.perfilRole = perfilRole;
    }

    public Integer getTentativasOtp() {
        return tentativasOtp;
    }

    public void setTentativasOtp(Integer tentativasOtp) {
        this.tentativasOtp = tentativasOtp;
    }

    public LocalDateTime getBloqueadoAte() {
        return bloqueadoAte;
    }

    public void setBloqueadoAte(LocalDateTime bloqueadoAte) {
        this.bloqueadoAte = bloqueadoAte;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    @PrePersist
    public void setCriadoEm() {
        if(criadoEm == null) criadoEm = LocalDateTime.now();
    }
    
    
    
    
}
