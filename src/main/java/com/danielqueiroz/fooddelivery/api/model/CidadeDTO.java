package com.danielqueiroz.fooddelivery.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representação de uma Cidade")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO>{

	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Londrina")
    private String nome;
    
	private EstadoDTO estado;
	
}
