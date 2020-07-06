package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "FotoProduto", description = "Representação de uma Foto de um produto")
@Setter
@Getter
public class FotoProdutoDTO {

	@ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Pizza-calabresa.jpg")
	private String nomeArquivo;
	
	@ApiModelProperty(example = "Pizza Calabresa")
	private String descricao;
	
	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "2512")
	private Long tamanho;
	
}
