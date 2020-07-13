package com.danielqueiroz.fooddelivery.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.danielqueiroz.fooddelivery.api.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CozinhasModel")
@Data
public class CozinhasModelOpenApi {
	
	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi {
		private List<CozinhaDTO> cozinhas;
		
	}
	
}
