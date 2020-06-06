package com.danielqueiroz.fooddelivery.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public EstadoNaoEncontradoException(Long id) {
		this(String.format("Estado com código %d não encontrado", id));
	}
	
}
