package com.danielqueiroz.fooddelivery.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * Anotação baseada na regra de negocio - "Se restaurante possue taxa de frete igual a zero, deve possuir 'Frete gratis' em nome "
 * 
 * @author Daniel Queiroz
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValorZeroIncluiDescricaoValidator.class})
public @interface ValorZeroIncluiDescricao {

	String message() default "descrição é obrigatória";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String valorField();
	String descricaoField();
	String descricaoObrigatoria();
}
