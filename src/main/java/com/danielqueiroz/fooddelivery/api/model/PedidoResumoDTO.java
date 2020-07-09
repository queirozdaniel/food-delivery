package com.danielqueiroz.fooddelivery.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Pedido", description = "Representação de um Pedido")
@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO> {

	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
	private String codigo;
	
	@ApiModelProperty(example = "95.00")
    private BigDecimal subtotal;
    
	@ApiModelProperty(example = "15.00")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "120.00")
    private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")	
	private String status;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z")	
	private OffsetDateTime dataCriacao;
	
	
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;

}
