/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.model;

/**
 *
 * @author BEATRICE
 */
public class LojaCountDTO {
    private Long id;
    private String nomeLoja;
    private Long total;
    
    public LojaCountDTO() {
    }

    public LojaCountDTO(Long id, String nomeLoja, Long total) {
        this.id = id;
        this.nomeLoja = nomeLoja;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    
    
    
}
