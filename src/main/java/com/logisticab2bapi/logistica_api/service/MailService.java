/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.logisticab2bapi.logistica_api.service;


import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 
 * Fonte: https://medium.com/@udnfahim/mail-verification-using-smtp-gmail-spring-boot-guide-30bc9cedc47a
 * Fonte2: https://medium.com/orangejuicefc/servi%C3%A7o-de-envio-de-e-mail-utilizando-java-spring-boot-e-thymeleaf-97241b0e0cf7
 * app mail senha = fmobspduqkdjysaq pelo google 
 *
 * @author Aluno
 */@Service
public class MailService{
    @Autowired private JavaMailSender mailSender;
    @Value("${spring.mail.username:entreexpress@teste.com}") private String remetente;

    // usado quando gera OTP no EM_TRANSITO
    public void enviarCodigoRastreio(String para, String codigo, String otp){
        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(remetente);
            msg.setTo(para);
            msg.setSubject("Seu pacote EntreExpress - " + codigo);
            msg.setText("Seu código de rastreio: " + codigo + 
                       "\nSeu código de entrega (informe ao entregador): " + otp +
                       "\nValido por 24h");
            mailSender.send(msg);
        } catch(Exception e){
            System.out.println("Email não enviado: " + e.getMessage());
        }
    }

    // sobrecarga pra quando você só quer avisar o rastreio (resolve seu erro)
    public void enviarCodigoRastreio(String para){
        enviarCodigoRastreio(para, "SEU CODIGO", "000000");
    }

    public void enviarConfirmacaoEntrega(String para, String codigo){
        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(remetente);
            msg.setTo(para);
            msg.setSubject("Pacote entregue - " + codigo);
            msg.setText("Seu pacote " + codigo + " foi entregue com sucesso!");
            mailSender.send(msg);
        } catch(Exception e){
            System.out.println("Email confirmação não enviado: " + e.getMessage());
        }
    }
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