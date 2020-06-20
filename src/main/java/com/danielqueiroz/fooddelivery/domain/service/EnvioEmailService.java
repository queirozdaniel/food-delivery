package com.danielqueiroz.fooddelivery.domain.service;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);

	@Getter
	@Setter
	class Mensagem {
		
		private Set<String> destinatarios;
		private String assunto;
		private String corpo;
	}
	
}
