/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

//import com.logisticab2bapi.logistica_api.model.OtpTentativaDTO;
import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.model.StatusHistorico;
//import com.logisticab2bapi.logistica_api.repository.OtpTentativaRepository;
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
    //@Autowired private OtpTentativaRepository otpRepo;
    @Autowired private NotificacaoService notif;
    

    private final List<String> FLUXO = List.of("Criado","Coletado","Em transito","Entregue","Devolvido");

    
    public Pacote criar(Pacote p){
        String codigo = "LON" + Year.now().getValue() + String.format("%04d", pacoteRepo.count() + 1);
        p.setCodigoLon(codigo);
        p.setStatusAtual("Criado");
        Pacote salvo = pacoteRepo.save(p);
        
        salvarHistorico(salvo.getId(), "Criado", 1L, "Remessa criada");
        return salvo;
    }

    
    public Pacote buscarPorCodigo(String codigo){
        return pacoteRepo.findByCodigoLon(codigo);
    }

    
    public Pacote atualizar(Long id, String novoStatus, String otp, String perfil){
        Pacote p = pacoteRepo.findById(id).orElseThrow();
        
        // RN03 - não pular
        int atual = FLUXO.indexOf(p.getStatusAtual());
        int novo = FLUXO.indexOf(novoStatus);
        if(novo != atual + 1) throw new RuntimeException("Status inválido");

        if(novoStatus.equals("Entregue") && !"entregador".equals(perfil))
            throw new RuntimeException("Só entregador entrega");

        // gera OTP
        if(novoStatus.equals("Em transito")){
            p.setOtpCodigo(String.format("%06d", new Random().nextInt(999999)));
            p.setOtpExpira(LocalDateTime.now().plusHours(24));
        }

        
        if(novoStatus.equals("Entregue")){
            //validarOtp(p, otp);
        }

        p.setStatusAtual(novoStatus);
        pacoteRepo.save(p);
        
        salvarHistorico(id, novoStatus, 1L, "Atualizado para " + novoStatus);
        notif.enviarEmail("loja@teste.com", p.getCodigoLon() + " -> " + novoStatus); // RF05
        
        return p;
    }
    
    private void validarOtp(Pacote p, String otp){
        if(p.getOtpCodigo() == null || !p.getOtpCodigo().equals(otp)){
            throw new RuntimeException("OTP inválido");
    }
        if(p.getOtpExpira() == null || p.getOtpExpira().isBefore(LocalDateTime.now())){
            throw new RuntimeException("OTP expirado");
    }
    
    }
    private void salvarHistorico(Long idPacote, String status, Long idUsuario, String obs){
        StatusHistorico h = new StatusHistorico();
        
        h.setId(idPacote);
        h.setStatus(status);
        h.setDataHora(LocalDateTime.now());
        h.setDescObserv(obs);
        h.setIdUsuario(idUsuario);
        histRepo.save(h); 
        }
}
