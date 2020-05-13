package com.danielqueiroz.fooddelivery.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_US0("/entidade-em-uso", "Entidade está sendo usada"),
	QUEBRA_NEGOCIO("/quebra-regra-de-negocio", "Violação em regra de negócio"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível");
	
	private String title;
	private String path;
	
	private ProblemType(String path, String title) {
		this.title = title;
		this.path = "http://localhost:8080" + path;
	}
	
}
