package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Endereco", description = "Representação de uma Endereco")
@Getter
@Setter
public class EnderecoDTO {

	@ApiModelProperty(example = "38400-000")
	private String cep;
	
	@ApiModelProperty(example = "Rua Santos")
	private String logradouro;
	
	@ApiModelProperty(example = "'1000'")
	private String numero;
	
	@ApiModelProperty(example = "Apto 2")
	private String complemento;
	
	@ApiModelProperty(example = "Centro")
	private String bairro;
	
	private CidadeResumoDTO cidade;
	
}
