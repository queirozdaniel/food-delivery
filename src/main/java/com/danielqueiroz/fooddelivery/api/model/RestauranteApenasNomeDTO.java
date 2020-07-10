package com.danielqueiroz.fooddelivery.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "RestauranteApenasNome", description = "Representação de um RestauranteApenasNome")
@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteApenasNomeDTO extends RepresentationModel<RestauranteApenasNomeDTO> {

    @ApiModelProperty(example = "1")
    private Long id;
    
    @ApiModelProperty(example = "SuperDogs")
    private String nome;
    
}     