package com.danielqueiroz.fooddelivery.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@ApiModel(value = "Usuario", description = "Representação de um Usuario")
@Setter
@Getter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Daniel Queiroz")
	private String nome;
	
	@ApiModelProperty(example = "daniel.qs@gmail.com")
	private String email;

}
