package com.danielqueiroz.fooddelivery.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioInput {

	@ApiModelProperty(example = "Daniel da Silva", required = true)
	@NotBlank
    private String nome;
    
	@ApiModelProperty(example = "daniel.s@gmail.com", required = true)
    @NotBlank
    @Email
    private String email;
	
}
