package com.danielqueiroz.fooddelivery.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	InputStream recuperar(String nomeArquivo);
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID() + "_" + nomeOriginal;
	}
	
	@Getter
	@Builder
	class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
		private String contentType;
		private Long contentLength;
		
	}
	
}
