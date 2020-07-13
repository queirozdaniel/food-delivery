package com.danielqueiroz.fooddelivery.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Grupo", description = "Representação de um Grupo")
@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoDTO extends RepresentationModel<GrupoDTO>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Gerente")
	private String nome;
	
}
