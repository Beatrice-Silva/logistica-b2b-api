/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;

//import com.logisticab2bapi.logistica_api.model.OtpTentativaDTO;
import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.model.Pacote.StatusAtual;
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

    private final List<String> FLUXO = List.of("CRIADO","COLETADO","EM_TRANSITO","ENTREGUE");

    public Pacote criar(Pacote p){
        String codigo = "LON" + Year.now().getValue() + String.format("%04d", pacoteRepo.count()+1);
        p.setCodigoRastreio(codigo);
        p.setStatusAtual(StatusAtual.CRIADO);
        Pacote salvo = pacoteRepo.save(p);
        salvarHistorico(salvo.getId(), "CRIADO", 1L, "Remessa criada");
        return salvo;
    }

    public Pacote buscarPorCodigo(String codigo){
        return pacoteRepo.findByCodigoRastreio(codigo).orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
    }

    public Pacote atualizar(Long id, String novoStatus, String otp, String perfil){
        Pacote p = pacoteRepo.findById(id).orElseThrow();
        // validação simples do fluxo
        int atual = FLUXO.indexOf(p.getStatusAtual().name());
        int novo = FLUXO.indexOf(novoStatus);
        if(novo != atual + 1) throw new RuntimeException("Status inválido, não pode pular etapa");
        
        if(novoStatus.equals("EM_TRANSITO")){
            p.setOtpCodigo(String.format("%06d", new Random().nextInt(999999)));
            p.setOtpExpira(LocalDateTime.now().plusHours(24));
        }
        p.setStatusAtual(StatusAtual.valueOf(novoStatus));
        return pacoteRepo.save(p);
    }

    private void salvarHistorico(Long idPacote, String status, Long idUsuario, String obs){
        StatusHistorico h = new StatusHistorico();
        h.setIdPacote(idPacote); h.setStatus(status); h.setDataHora(LocalDateTime.now());
        histRepo.save(h);
    }
}
