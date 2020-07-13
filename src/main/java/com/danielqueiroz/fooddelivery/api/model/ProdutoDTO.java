package com.danielqueiroz.fooddelivery.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Produto", description = "Representação de um Produto")
@Relation(collectionRelation = "produtos")
@Setter
@Getter
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Pizza Calabresa")
    private String nome;
	
	@ApiModelProperty(example = "Pizza tradicinal, com muita calabresa e cebola")
    private String descricao;
	
	@ApiModelProperty(example = "28.00")
    private BigDecimal preco;
	
	@ApiModelProperty(example = "true")
    private Boolean ativo; 
	
}
