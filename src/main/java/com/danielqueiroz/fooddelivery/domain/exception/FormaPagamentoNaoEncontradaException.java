package com.danielqueiroz.fooddelivery.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de forma de pagamento com código %d", id));
    }
	
}
