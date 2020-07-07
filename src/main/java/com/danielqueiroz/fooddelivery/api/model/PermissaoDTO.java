package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Permissao", description = "Representação de uma Permissão de um Grupo")
@Setter
@Getter
public class PermissaoDTO {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "CONSULTAR_COZINHAS")
	private String nome;
	
	@ApiModelProperty(example = "Permite consultar cozinhas")
	private String descricao;
	
}
