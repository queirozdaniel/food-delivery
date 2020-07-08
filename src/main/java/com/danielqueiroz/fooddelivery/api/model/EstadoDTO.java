package com.danielqueiroz.fooddelivery.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Estado", description = "Representação de um Estado")
@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

	@ApiModelProperty(value = "ID do Estado", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Paraná")
	private String nome;
	
}
