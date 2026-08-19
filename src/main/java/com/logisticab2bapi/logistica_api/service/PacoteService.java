




package com.logisticab2bapi.logistica_api.service;
import com.logisticab2bapi.logistica_api.model.Loja;
import com.logisticab2bapi.logistica_api.model.LojaCountDTO;
import com.logisticab2bapi.logistica_api.model.Pacote;
import static com.logisticab2bapi.logistica_api.model.Pacote.StatusAtual.CRIADO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
    
    //nao pode pular fluxo !
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
        if(p.getEmailDestinatario() == null || p.getEmailDestinatario().isBlank()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email destinatário obrigatório para envio do OTP!");
        }
        
        

        Loja lojaReal = lojaRepo.findById(p.getLoja().getId()).orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        if(!lojaReal.getAtivo()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Loja inativa não recebe remessa - RN02");
        }
        p.setLoja(lojaReal);
        
        
        String codigo = "LON" + Year.now().getValue() + String.format("%04d", pacoteRepo.count()+1);
        
        p.setCodigoRastreio(codigo);
        p.setStatusAtual(Pacote.StatusAtual.CRIADO);

        Pacote salvo = pacoteRepo.save(p);
        salvarHistorico(salvo.getId(), "CRIADO", salvo.getLoja().getId(), "Remessa criada" + usuarioLogado.getEmail());
        
    try {
        mailService.enviarCodigoRastreio(salvo.getEmailDestinatario(), salvo.getCodigoRastreio(), salvo.getOtpCodigo());
        } catch(Exception e){ // salvo.getEmailDestinatario(), salvo.getCodigoRastreio()
            System.out.println("E-mail não enviado na criação: " + e.getMessage());
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
    
    public Pacote atualizar(Long id, String novoStatus, String otp, String token, String perfil){
        Pacote p = pacoteRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pacote não encontrado"));
        Usuario logado = tokenService.extrairClaim(token);

        int idxAtual = FLUXO.indexOf(p.getStatusAtual().name());
        int idxNovo = FLUXO.indexOf(novoStatus.toUpperCase());
        
        
        if(idxNovo != idxAtual + 1 && !novoStatus.equalsIgnoreCase("ARQUIVADO")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fluxo inválido. Atual: " + p.getStatusAtual() + " não pode ir para " + novoStatus);
        }

        if("EM_TRANSITO".equalsIgnoreCase(novoStatus)){
            
            String otpGerado = String.format("%06d", new Random().nextInt(999999));
            p.setOtpCodigo(otpGerado);
            p.setOtpExpira(LocalDateTime.now().plusHours(24));
            p.setStatusAtual(Pacote.StatusAtual.EM_TRANSITO);
            pacoteRepo.save(p);
            
           
            mailService.enviarCodigoRastreio(p.getEmailDestinatario(), p.getCodigoRastreio(), otpGerado);
        } 
        else if(novoStatus.equalsIgnoreCase("ENTREGUE")){
            if(logado.getPerfilRole() != Usuario.PerfilRole.ENTREGADOR && logado.getPerfilRole() != Usuario.PerfilRole.OPERADOR){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Só entregador pode entregar");
            }
            if(p.getOtpCodigo() == null || !p.getOtpCodigo().equals(otp)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP inválido para entrega");
            }
            if(p.getOtpExpira() != null && p.getOtpExpira().isBefore(LocalDateTime.now())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expirado, gere novamente");
            }
            p.setStatusAtual(Pacote.StatusAtual.ENTREGUE);
            mailService.enviarConfirmacaoEntrega(p.getEmailDestinatario(), p.getCodigoRastreio());
        } else {
            p.setStatusAtual(Pacote.StatusAtual.valueOf(novoStatus.toUpperCase()));
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
        h.setIdUsuario(idUsuario);
        h.setStatus(status); 
        h.setDataHora(LocalDateTime.now());
        h.setDescObserv(obs);
        histRepo.save(h);
    }
    
    
    }
    /*
     public Pacote criar(Pacote novo, String token){
        Usuario logado = tokenService.extrairClaim(token);
        mailService.enviarCodigoRastreio(novo.getEmailDestinatario(), novo.getCodigoRastreio());
        novo.setCodigoRastreio("EE" + System.currentTimeMillis());
        novo.setStatusAtual(CRIADO);
        novo.setOtpCodigo(String.format("%06d", new Random().nextInt(999999)));
        Pacote salvo = pacoteRepo.save(novo);

        
        mailService.enviarCodigoRastreio(
            salvo.getEmailDestinatario(), 
            salvo.getCodigoRastreio(),
            salvo.getOtpCodigo()
        );
        return salvo;
     }
   }
    
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
    
    
    
    


