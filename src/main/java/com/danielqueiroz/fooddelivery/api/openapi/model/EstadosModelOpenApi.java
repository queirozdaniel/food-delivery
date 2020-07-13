package com.danielqueiroz.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.danielqueiroz.fooddelivery.api.model.EstadoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {

	private EstadosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("EstadosEmbeddedModel")
    @Data
    public class EstadosEmbeddedModelOpenApi {
        
        private List<EstadoDTO> estados;
        
    }
}
