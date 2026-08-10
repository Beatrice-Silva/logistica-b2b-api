




package com.logisticab2bapi.logistica_api.service;
import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.Pacote;
import com.logisticab2bapi.logistica_api.model.StatusHistorico;
import com.logisticab2bapi.logistica_api.model.Usuario;
import com.logisticab2bapi.logistica_api.model.Usuario.PerfilRole;
import com.logisticab2bapi.logistica_api.repository.LojaRepository;
import com.logisticab2bapi.logistica_api.repository.PacoteRepository;
import com.logisticab2bapi.logistica_api.repository.StatusHistoricoRepository;
import java.time.LocalDateTime;
import java.time.Year;

import java.util.List;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service 
public class PacoteService {
    
    @Autowired 
    private PacoteRepository pacoteRepo;
    @Autowired 
    private StatusHistoricoRepository histRepo;
    @Autowired 
    private LojaRepository lojaRepo;
    @Autowired 
    private NotificacaoService notificacaoService;
    @Autowired 
    private TokenService tokenService;
    @Autowired 
    private MailService mailService;
    
    private final List<String> FLUXO = 
            List.of("CRIADO","COLETADO","EM_TRANSITO","ENTREGUE","ARQUIVADO");

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
        }

        Loja lojaReal = lojaRepo.findById(p.getLoja().getId()).orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        p.setLoja(lojaReal);

        String codigo = "LON" + Year.now().getValue() + String.format("%04d", pacoteRepo.count()+1);
        
        p.setCodigoRastreio(codigo);
        p.setStatusAtual(Pacote.StatusAtual.CRIADO);

        Pacote salvo = pacoteRepo.save(p);
        salvarHistorico(salvo.getId(), "CRIADO", salvo.getLoja().getId(), "Remessa criada");
        
    try {
        notificacaoService.enviarEmail("log@teste.com", "Criado: " + codigo);
    } catch(Exception e){
        System.out.println("Email não enviado: " + e.getMessage());
    }
    
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
            mailService.sendOtp(p.getEmailDestinatario());
            p.setStatusAtual(Pacote.StatusAtual.valueOf(novoStatus.toUpperCase()));
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
        salvarHistorico(salvo.getId(), novoStatus.toUpperCase(),salvo.getLoja().getId(),"Atualizado por " + perfil);             
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
   
    /*
   public Map<String, Long> getCounts(String token) {//repo direto
    List<Object[]> resultados = pacoteRepo.contarPorStatus();
    Map<String, Long> map = new HashMap<>();
    map.put("CRIADO", 0L);
    map.put("COLETADO", 0L);
    map.put("EM_TRANSITO", 0L);
    map.put("ENTREGUE", 0L);
    map.put("DEVOLVIDO", 0L);
    map.put("ARQUIVADO", 0L);

    for(Object[] row : resultados) {
        String status = row[0].toString(); 
        Long total = (Long) row[1];
        map.put(status, total);
    }
    return map;
} 
  */  
    
    
    
    
}

