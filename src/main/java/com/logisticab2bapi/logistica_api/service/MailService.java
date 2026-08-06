/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;


import java.time.Instant;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 
 * Fonte: https://medium.com/@udnfahim/mail-verification-using-smtp-gmail-spring-boot-guide-30bc9cedc47a
 * Fonte2: https://medium.com/orangejuicefc/servi%C3%A7o-de-envio-de-e-mail-utilizando-java-spring-boot-e-thymeleaf-97241b0e0cf7
 * app mail senha = fmob spdu qkdj ysaq
 *
 * @author Aluno
 */
@Service
public class MailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    private static final String TEMPLATE_NAME = "Registro";
    
    private static final String TEMPLATE_NAME = "Registro";
    
    private static final String TEMPLATE_NAME = "Registro";
    
    private static final String TEMPLATE_NAME = "Registro";
    
    //email que recebera 
    final String adminMail ="Aimed09513";
    
    
    //Exige Otp e expiracao  =  abritos a seguir devem ser deifinidos
    private String ultimoOtp;//verificacao do otp
    
    private Instant otpHora;//renforça o tempo para a expiracao
    
    //method suporte para gerar e enviar o OTP 
    public String sendOtp(String client){//gera otp 5 digitos aleatorios 
       
        //salvar e tempo
        String otp = String.format("%05d", new Random().nextInt(100000));
        this.ultimoOtp = otp;
        
        this.otpHora = Instant.now();
        
        //Permite validacao e verificacao do tempo de expiracao
        
        //Corpo da mensagem
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(adminMail);        
        mensagem.setTo(client);                
        mensagem.setSubject("aimed09513@gmail.com");                
        mensagem.setText("Seu Otp é" + otp);        
        mailSender.send(mensagem);
        
        return otp;
        //retornar ajuda com a logica da verificacao, testar e debugar
    }
    /*
    injete-o aonde for preciso, seja Controller ou Auth Service
    
    Send OTP
    
    Verify OTP
    
    Enforce expiration logic
    
    Diferenca de envio email e email SMTP
    O envio de e-mail envolve um processo mais complexo que inclui o envio da mensagem,
    a autenticação e a entrega da mensagem ao destinatário. O SMTP é apenas uma parte desse processo.
    Ele é responsável por enviar a mensagem do cliente (como um aplicativo de e-mail) para o servidor 
    SMTP e, em seguida, para o servidor do destinatário. O processo completo envolve o cliente e o 
    servidor "conversando" usando comandos específicos. O SMTP não é responsável por baixar mensagens,
    não as exibe no seu aplicativo e não as armazena para leitura. Para receber mensagens, 
    outros protocolos como IMAP ou POP3 entram em cena.
    */
    
    
    
    
    
   
    
    
    
}
