package com.danielqueiroz.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.danielqueiroz.fooddelivery.api.model.PedidoResumoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Getter
@Setter
public class PedidosModelOpenApi {

	private PedidosResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;
    
    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    public class PedidosResumoEmbeddedModelOpenApi {
        
        private List<PedidoResumoDTO> pedidos;
        
    } 
	
}
