package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CidadeResumo", description = "Representação de uma Cidade mias resumida")
@Getter
@Setter
public class CidadeResumoDTO {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Londrina")
    private String nome;
    
    @ApiModelProperty(example = "Paraná")
    private String estado;
	
}
