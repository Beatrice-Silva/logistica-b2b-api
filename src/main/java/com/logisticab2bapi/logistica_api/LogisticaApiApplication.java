package com.logisticab2bapi.logistica_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //<- Anotation responsavel por permitir o envio de emails
//ao usuario !

/* A Annotation Ennable Async habilita o suporte a execucao assincrona.
Permitindo que a aplicacao gerencie o fluxo de threads para processar
requisicoes de forma que nao bloqueie
Solucao adotada para atender ao requisito de envio de e-mail com OTP
sem bloquear a requisicao do usuario. 
*/
public class LogisticaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticaApiApplication.class, args);
	}

}

/*
A aplicacao entendera que as threads devem ser excutadas de forma paralela
e nao bloqueante, seguindo a configuracao padrao do Spring. 
No entanto, e possivel realizar ajustes, 
dependendo das necessidades especificas de cada aplicacao.

Para isso a abordagem assincrona do Spring permite configurar
um ThreadPoolTaskExecutor por meio de uma classe de configuracao que define 
um bean especifico. Esse bean retorna uma instancia do TPTE, que gerencia
a execucao das threads e permite um controle mais detalhado
sobre a execucao das tarefas

*/
