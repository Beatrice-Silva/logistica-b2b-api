/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

import com.logisticab2bapi.logistica_api.model.OtpTentativaDTO;
import com.logisticab2bapi.logistica_api.model.PacoteDTO;
import com.logisticab2bapi.logistica_api.model.StatusHistoricoDTO;
import com.logisticab2bapi.logistica_api.repository.OtpTentativaRepository;
import com.logisticab2bapi.logistica_api.repository.PacoteRepository;
import com.logisticab2bapi.logistica_api.repository.StatusHistoricoRepository;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BEATRICE
 */
@Service
public class PacoteService {
    @Autowired private PacoteRepository pacoteRepo;
    @Autowired private StatusHistoricoRepository histRepo;
    @Autowired private OtpTentativaRepository otpRepo;
    @Autowired private NotificacaoService notif;
    

    private final List<String> FLUXO = List.of("Criado","Coletado","Em transito","Entregue","Devolvido");

    
    public PacoteDTO criar(PacoteDTO p){
        String codigo = "LON" + Year.now().getValue() + String.format("%04d", pacoteRepo.count() + 1);
        p.setCodigoLon(codigo);
        p.setStatus_atual("Criado");
        PacoteDTO salvo = pacoteRepo.save(p);
        
        salvarHistorico(salvo.getId(), "Criado", 1L, "Remessa criada");
        return salvo;
    }

    
    public PacoteDTO buscarPorCodigo(String codigo){
        return pacoteRepo.findByCodigoLon(codigo);
    }

    
    public PacoteDTO atualizar(Long id, String novoStatus, String otp, String perfil){
        PacoteDTO p = pacoteRepo.findById(id).orElseThrow();
        
        // RN03 - não pular
        int atual = FLUXO.indexOf(p.getStatus_atual());
        int novo = FLUXO.indexOf(novoStatus);
        if(novo != atual + 1) throw new RuntimeException("Status inválido");

        if(novoStatus.equals("Entregue") && !"entregador".equals(perfil))
            throw new RuntimeException("Só entregador entrega");

        // gera OTP
        if(novoStatus.equals("Em transito")){
            p.setOtp_codigo(String.format("%06d", new Random().nextInt(999999)));
            p.setOtp_expira(LocalDateTime.now().plusHours(24));
        }

        
        if(novoStatus.equals("Entregue")){
            validarOtp(p, otp);
        }

        p.setStatus_atual(novoStatus);
        pacoteRepo.save(p);
        
        salvarHistorico(id, novoStatus, 1L, "Atualizado para " + novoStatus);
        notif.enviarEmail("loja@teste.com", p.getCodigoLon() + " -> " + novoStatus); // RF05
        
        return p;
    }

    private void validarOtp(PacoteDTO p, String otp){
        OtpTentativaDTO t = otpRepo.findByIdPacote(p.getId()).orElse(new OtpTentativaDTO());
        t.setIdPacote(p.getId());
        
        if(t.getBloqueioAte() != null && t.getBloqueioAte().isAfter(LocalDateTime.now()))
            throw new RuntimeException("Bloqueado 30min");
            
        if(!p.getOtp_codigo().equals(otp) || p.getOtp_expira().isBefore(LocalDateTime.now())){
            t.setTentativas(t.getTentativas() + 1);
            if(t.getTentativas() >= 3){
                t.setBloqueioAte(LocalDateTime.now().plusMinutes(30));
                t.setTentativas(0);
            }
            otpRepo.save(t);
            throw new RuntimeException("OTP inválido");
        }
        otpRepo.delete(t);
    }

    private void salvarHistorico(Long idPacote, String status, Long idUsuario, String obs){
        StatusHistoricoDTO h = new StatusHistoricoDTO();
        h.setId_pacote(idPacote);
        h.setStatus(status);
        h.setData_hora(LocalDateTime.now());
        h.setId_usuario(idUsuario);
        h.setDesc_observ(obs);
        histRepo.save(h); // RF10
        }
}
