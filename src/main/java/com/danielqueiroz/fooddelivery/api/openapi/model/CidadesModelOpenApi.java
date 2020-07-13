package com.danielqueiroz.fooddelivery.api.openapi.model;

import java.util.List;

import com.danielqueiroz.fooddelivery.api.model.CidadeDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadesModel")
@Getter
@Setter
public class CidadesModelOpenApi {

	private CidadeEmbeddedModelOpenApi _embedded;
	
	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpenApi {
		private List<CidadeDTO> cidades;
		
	}
	
}
