package com.danielqueiroz.fooddelivery.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "RestauranteResumo", description = "Representação de um RestauranteResumo")
@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteResumoDTO extends RepresentationModel<RestauranteResumoDTO>{

	@ApiModelProperty(example = "2")
	private Long id;
	
	@ApiModelProperty(example = "Pizzarione")
	private String nome;
	
}
