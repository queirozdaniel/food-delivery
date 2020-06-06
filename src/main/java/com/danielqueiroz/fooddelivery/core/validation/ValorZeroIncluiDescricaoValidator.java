package com.danielqueiroz.fooddelivery.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		this.valorField = constraintAnnotation.valorField();	
		this.descricaoField = constraintAnnotation.descricaoField();
		this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean validacao = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(value.getClass(), valorField)
					.getReadMethod().invoke(value);			
			String descricao = (String) BeanUtils.getPropertyDescriptor(value.getClass(), descricaoField)
					.getReadMethod().invoke(value);	
			if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
				validacao = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
			
			return validacao;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
		
	}

}
