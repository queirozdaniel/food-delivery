package com.danielqueiroz.fooddelivery;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.service.CozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CozinhaIntegrationTestsIT {

	@Autowired
	private CozinhaService cozinhaService;

	@Test
	public void deveTerSucessoAoCadastroCozinhaComCamposValidos() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		novaCozinha = cozinhaService.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void deveFalharAoCadastrarCozinhaQuandoEstiverSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("");
		
		novaCozinha = cozinhaService.salvar(novaCozinha);
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalharAoTentarExcluirCozinhaEmUso() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setId(1L);
		
		cozinhaService.deletar(novaCozinha.getId());
	}
	
	@Test(expected = EntidadeNaoEncontradaException.class)	
	public void deveFalharAoTentarExcluirCozinhaInexistente() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setId(30L);
		
		cozinhaService.deletar(novaCozinha.getId());
	}
	
	
}
