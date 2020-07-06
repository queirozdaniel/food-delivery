package com.danielqueiroz.fooddelivery.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedModelApi<T> {

	private List<T> content;
	
	@ApiModelProperty(example= "10", value = "Quantidade de registros por página")
	private Long size;
	
	@ApiModelProperty(example= "30", value = "Quantidade total de registros")
	private Long totalElements;
	
	@ApiModelProperty(example= "3", value = "Total de páginas")
	private Long totalPages;
	
	@ApiModelProperty(example= "0", value = "Número da pagina atual (começa em 0)")
	private Long number;
	
}
