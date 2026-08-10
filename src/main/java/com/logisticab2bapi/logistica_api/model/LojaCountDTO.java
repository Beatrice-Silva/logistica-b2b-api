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
    private String nomeLoja;
    private Long total;
    
    public LojaCountDTO() {
    }

    public LojaCountDTO(String nomeLoja, Long total) {
        this.nomeLoja = nomeLoja;
        this.total = total;
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
