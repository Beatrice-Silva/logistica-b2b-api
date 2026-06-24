package com.logisticab2bapi.logistica_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //<- Anotation responsavel por permitir o envio de emails
//ao usuario !
public class LogisticaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticaApiApplication.class, args);
	}

}
