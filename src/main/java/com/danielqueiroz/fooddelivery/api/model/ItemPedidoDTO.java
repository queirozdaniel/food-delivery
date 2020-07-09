package com.danielqueiroz.fooddelivery.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "ItemPedido", description = "Representação de um Item de um Pedido")
@Setter
@Getter
public class ItemPedidoDTO extends RepresentationModel<ItemPedidoDTO> {

	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Pizza calabresa")
    private String produtoNome;
	
	@ApiModelProperty(example = "2")
    private Integer quantidade;
	
	@ApiModelProperty(example = "30.0")
    private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "60.0")
    private BigDecimal precoTotal;
    
	@ApiModelProperty(example = "Com bastante cebola")
	private String observacao; 
	
}
