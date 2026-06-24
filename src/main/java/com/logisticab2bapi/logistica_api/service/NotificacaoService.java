/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author BEATRICE
 */
@Service
public class NotificacaoService {
      @Async //@EnableAsync na main
    public void enviarEmail(String para, String msg){
        System.out.println("[ASYNC] Email para " +para+ ": "+msg); 
    }
}
