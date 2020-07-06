package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "FormaPagamento", description = "Representação de uma Forma de Pagamento")
@Getter
@Setter
public class FormaPagamentoDTO {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cartão de débito")
	private String descricao;
	
}
