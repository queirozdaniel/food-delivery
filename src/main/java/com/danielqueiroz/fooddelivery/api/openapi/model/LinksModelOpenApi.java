package com.danielqueiroz.fooddelivery.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Links")
@Getter
@Setter
public class LinksModelOpenApi {

	private LinkModel rel;
	
	@ApiModel("Link")
	@Getter
	@Setter
	private class LinkModel {
		private String href;
		private boolean templated;
	}
	
}
