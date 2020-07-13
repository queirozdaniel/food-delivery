package com.danielqueiroz.fooddelivery.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("PageModel")
@Data
public class PageModelOpenApi {

	@ApiModelProperty(example= "10", value = "Quantidade de registros por página")
	private Long size;
	
	@ApiModelProperty(example= "30", value = "Quantidade total de registros")
	private Long totalElements;
	
	@ApiModelProperty(example= "3", value = "Total de páginas")
	private Long totalPages;
	
	@ApiModelProperty(example= "0", value = "Número da pagina atual (começa em 0)")
	private Long number;
	
}
