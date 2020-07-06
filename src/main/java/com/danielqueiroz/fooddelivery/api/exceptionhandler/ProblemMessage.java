package com.danielqueiroz.fooddelivery.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ProblemMessage {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 5)
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example = "https://api.fooddelivery.com.br/dados-invalidos", position = 10)
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 15)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", 
			position = 20)
	private String detail;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", 
			position = 25)
	private String userMessage;
	
	@ApiModelProperty(value = "Objetos ou campos que geraram o erro", position = 30)
	private List<Field> fields;
	
	@ApiModel("Objeto Problema")
	@Getter
	@Builder
	public static class Field {
		@ApiModelProperty(example = "preco")
		private String nome;
		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}
}
