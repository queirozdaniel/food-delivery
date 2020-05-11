package com.danielqueiroz.fooddelivery.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Embeddable
public class Endereco {

	@Column(name ="endereco_cep", nullable = true)
	private String cep;
	
	@Column(name ="endereco_logradouro",nullable = true)
	private String logradouro;
	
	@Column(name ="endereco_numero",nullable = true)
	private String numero;
	
	@Column(name ="endereco_complemento",nullable = true)
	private String complemento;
	
	@Column(name ="endereco_bairro",nullable = true)
	private String bairro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="endereco_cidade_id", nullable = true)
	private Cidade cidade;
	
}
