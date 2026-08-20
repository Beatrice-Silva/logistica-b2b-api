package com.logisticab2bapi.logistica_api.service;

import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.model.StatusHistorico;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.model.Usuario.PerfilRole;
import com.logisticab2bapi.logistica_api.repository.LojaRepository;
import com.logisticab2bapi.logistica_api.repository.PacoteRepository;
import com.logisticab2bapi.logistica_api.repository.StatusHistoricoRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service 
public class PacoteService {
    
    @Autowired private PacoteRepository pacoteRepo;
    @Autowired private StatusHistoricoRepository histRepo;
    @Autowired private LojaRepository lojaRepo;
    @Autowired private TokenService tokenService;
    @Autowired private MailService mailService;
    
    private final List<String> FLUXO = List.of("CRIADO","COLETADO","EM_TRANSITO","ENTREGUE","DEVOLVIDO","ARQUIVADO");

    public Pacote novoPacote(Pacote p, Usuario usuarioLogado){
        if(usuarioLogado.getPerfilRole()!= PerfilRole.OPERADOR && usuarioLogado.getPerfilRole()!= PerfilRole.ADMIN){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: apenas Operadores");
        }
        if(p.getLoja() == null || p.getLoja().getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loja obrigatória");
        }
        if(p.getEnderecoDestino() == null || p.getEnderecoDestino().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço do destino não preenchido!");
        }
        if(p.getEmailDestinatario() == null || p.getEmailDestinatario().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email destinatário obrigatório para envio do OTP!");
        }

        Loja lojaReal = lojaRepo.findById(p.getLoja().getId()).orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        if(!lojaReal.getAtivo()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Loja inativa não recebe remessa");
        }
        p.setLoja(lojaReal);
        
        
        long next = pacoteRepo.count() + 1;
        String codigoLon = String.format("LON2026%02d%04d", LocalDate.now().getMonthValue(), next);
        String codigoRastreio = String.format("BRLON26%02d%04d", LocalDate.now().getMonthValue(), next);
        p.setCodigoLon(codigoLon);
        p.setCodigoRastreio(codigoRastreio);
        
        p.setStatusAtual(Pacote.StatusAtual.CRIADO);

        Pacote salvo = pacoteRepo.save(p);
        
        salvarHistorico(salvo.getId(), "CRIADO", usuarioLogado.getId(), "Remessa criada por " + usuarioLogado.getEmail());
        
        try {
            mailService.enviarCodigoRastreio(salvo.getEmailDestinatario(), salvo.getCodigoRastreio(), salvo.getOtpCodigo());
        } catch(Exception e){
            System.out.println("E-mail não enviado: " + e.getMessage());
        }
        return salvo;
    }   
    
    public Pacote buscarPorCodigo(String codigo){
        return pacoteRepo.findByCodigoRastreio(codigo).orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
    }
    
    public Pacote atualizar(Long id, String novoStatus, String otp, String token, String emailLogado){
    Pacote p = pacoteRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pacote não encontrado"));
    Usuario logado = tokenService.extrairClaim(token);
    System.out.println("DEBUG OTP - Pacote: " + p.getCodigoRastreio() + " | Banco: '" + p.getOtpCodigo() + "' | Digitado: '" + otp + "' | Expira: " + p.getOtpExpira());

    if("EM_TRANSITO".equalsIgnoreCase(novoStatus)){
        String otpGerado = String.format("%06d", new Random().nextInt(999999));
        p.setOtpCodigo(otpGerado);
        p.setOtpExpira(LocalDateTime.now().plusHours(24));
        p.setStatusAtual(Pacote.StatusAtual.EM_TRANSITO);
        if(p.getIdEntregador() == null) p.setIdEntregador(logado.getId());
        pacoteRepo.save(p);
        mailService.enviarOtpEntrega(p.getEmailDestinatario(), p.getCodigoRastreio(), p.getCodigoLon(), otpGerado);
        mailService.enviarOtpEntrega(p.getLoja().getContatoEmail(), p.getCodigoRastreio(), p.getCodigoLon(), otpGerado);
        salvarHistorico(p.getId(), "EM_TRANSITO", logado.getId(), "OTP " + otpGerado + " enviado");
        return p;
    } 
    else if("ENTREGUE".equalsIgnoreCase(novoStatus)){
        if(p.getOtpCodigo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum OTP gerado. Mude primeiro para EM_TRANSITO");
        }
        if(p.getOtpExpira() != null && p.getOtpExpira().isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expirado em " + p.getOtpExpira() + ". Gere novo em EM_TRANSITO");
        }
        String otpBanco = p.getOtpCodigo().trim();
        String otpDigitado = otp != null ? otp.trim() : "";
        // aceita 19518 e 019518 como igual
        if(!otpBanco.equals(otpDigitado) && !otpBanco.equals(String.format("%06d", Integer.parseInt(otpDigitado)))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP inválido. Esperado: " + otpBanco + " mas veio: " + otpDigitado);
        }
        p.setStatusAtual(Pacote.StatusAtual.ENTREGUE);
    } 
    else {
        // CRIADO -> COLETADO -> EM_TRANSITO
        if(FLUXO.indexOf(novoStatus.toUpperCase()) != FLUXO.indexOf(p.getStatusAtual().name()) + 1 && !novoStatus.equalsIgnoreCase("ARQUIVADO")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fluxo inválido. Atual: " + p.getStatusAtual() + " não pode ir para " + novoStatus);
        }
        if("COLETADO".equalsIgnoreCase(novoStatus)){
            p.setIdEntregador(logado.getId());
        }
        p.setStatusAtual(Pacote.StatusAtual.valueOf(novoStatus.toUpperCase()));
    }

    Pacote salvo = pacoteRepo.save(p);
    salvarHistorico(salvo.getId(), novoStatus.toUpperCase(), logado.getId(),"Atualizado por " + emailLogado);             
    return salvo;
}
    
    private void salvarHistorico(Long idPacote, String status, Long idUsuario, String obs){
        StatusHistorico h = new StatusHistorico();
        h.setIdPacote(idPacote); 
        h.setIdUsuario(idUsuario);
        h.setStatus(status); 
        h.setDataHora(LocalDateTime.now());
        h.setDescObserv(obs);
        histRepo.save(h);
    }
}