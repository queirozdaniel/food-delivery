package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representação de uma Cidade")
@Getter
@Setter
public class CidadeDTO {

	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Londrina")
    private String nome;
    
	private EstadoDTO estado;
	
}
