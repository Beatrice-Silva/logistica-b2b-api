/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;
import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.model.Pacote.StatusAtual;
import static com.logisticab2bapi.logistica_api.model.Pacote.StatusAtual.CRIADO;
import com.logisticab2bapi.logistica_api.model.StatusHistorico;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.repository.PacoteRepository;
import com.logisticab2bapi.logistica_api.repository.StatusHistoricoRepository;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PacoteService {
    
    @Autowired private PacoteRepository pacoteRepo;
    @Autowired private StatusHistoricoRepository histRepo;
    @Autowired private NotificacaoService notificacaoService;
    @Autowired private TokenService tokenService;
    
    private final List<String> FLUXO = 
            List.of("CRIADO","COLETADO","EM_TRANSITO","ENTREGUE","ARQUIVADO");

    public Pacote novoPacote(Pacote p, Usuario usuarioLogado){//adicionar token
        String message = "";
        
        if(!usuarioLogado.getPerfilRole().equals("OPERADOR")){
            throw new  ResponseStatusException(HttpStatusCode.valueOf(403),
                    "Acesso negado: apenas Operadores conseguem criar novos pacotes"
            );
        }
        
        if(p.getCodigoRastreio().isEmpty()){
            message += "Código de Rastreio não preenchido!";
        }
        if(p.getDescObserv().isEmpty()){
            message += "Descrição/Observação não preenchido!";
        }
        if(p.getEnderecoDestino().isEmpty()){
            message += "Endereço do destino não preenchido!";
        }
        if(p.getOtpCodigo().isEmpty()){
            message += "Código OTP não preenchido!";
        }
        if(!p.getStatusAtual().equals(CRIADO)){
            message += "É preciso que o status siga as etapas uma por vez";
        }
        if(!message.isEmpty()){
            throw new  ResponseStatusException(HttpStatusCode.valueOf(400), message);
            
        }
        
        String codigo = "LON" + Year.now().getValue() + String.format("%04d", pacoteRepo.count()+1);
        
        p.setCodigoRastreio(codigo);
        p.setStatusAtual(StatusAtual.CRIADO);
        
        Pacote salvo = pacoteRepo.save(p);
        salvarHistorico(salvo.getId(), "CRIADO", salvo.getIdLoja(), "Remessa criada");
        
        
        try {
            notificacaoService.enviarEmail(
                    salvo.getEnderecoDestino(), 
                    "Criado: " + codigo);
        } catch(Exception e){ System.out.println("Email não enviado: " + e.getMessage()); }
        
        return salvo;
    }
    
    public List<Pacote> listarPacote(String token){
        Usuario logado = tokenService.extrairClaim(token);
        return pacoteRepo.findAll();
    }

    public Pacote buscarPorCodigo(String codigo){
        return pacoteRepo
                .findByCodigoRastreio(codigo)
                .orElseThrow(() -> 
                new RuntimeException("Pacote não encontrado"));
    }

    public Pacote atualizar(Long id, String novoStatus, String otp, String perfil){
        Pacote p = pacoteRepo.findById(id).orElseThrow();
       
        int atual = FLUXO.indexOf(p.getStatusAtual().name()); 
        
        int novo = FLUXO.indexOf(novoStatus.toUpperCase());
        if(novo != atual + 1) throw new RuntimeException("Status inválido, não pode pular etapa");
        
        if(novoStatus.equalsIgnoreCase("EM_TRANSITO")){
            p.setOtpCodigo(String.format("%06d", new Random().nextInt(999999)));
            p.setOtpExpira(LocalDateTime.now().plusHours(24));
        }
        
       
        if(novoStatus.equalsIgnoreCase("ENTREGUE")){
            if(otp == null || !otp.equals(p.getOtpCodigo())){
                throw new RuntimeException("OTP inválido para entrega");
            }
            if(p.getOtpExpira() != null && p.getOtpExpira().isBefore(LocalDateTime.now())){
                throw new RuntimeException("OTP expirado");
            }
        }
        Pacote salvo = pacoteRepo.save(p);
        salvarHistorico(salvo.getId(), novoStatus.toUpperCase(),salvo.getIdLoja(),"Atualizado por " + perfil);             
        return salvo; 
        
    }

    private void salvarHistorico(
            Long idPacote, 
            String status, 
            Long idUsuario, 
            String obs){
        
        StatusHistorico h = new StatusHistorico();
        h.setIdPacote(idPacote); 
        h.setStatus(status); 
        h.setDataHora(LocalDateTime.now());
        h.setDescObserv(obs);
        histRepo.save(h);
    }
    
    
    
    
}
