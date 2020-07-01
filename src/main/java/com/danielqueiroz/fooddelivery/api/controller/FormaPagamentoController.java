package com.danielqueiroz.fooddelivery.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.danielqueiroz.fooddelivery.api.model.FormaPagamentoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.FormaPagamentoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.FormaPagamentoInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.FormaPagamentoInput;
import com.danielqueiroz.fooddelivery.domain.model.FormaPagamento;
import com.danielqueiroz.fooddelivery.domain.repository.FormaPagamentoRepository;
import com.danielqueiroz.fooddelivery.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoService formaPagamentoService;

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}

		if (request.checkNotModified(eTag)) {
			return null;
		}

		List<FormaPagamento> todasFormasPagamentos = formaPagamentoService.buscarTodos();
		List<FormaPagamentoDTO> formasPagamentoDTO = formaPagamentoDTOAssembler
				.toCollectionModel(todasFormasPagamentos);

		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()).eTag(eTag)
				.body(formasPagamentoDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id, ServletWebRequest request) {

		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String eTag = "0";

		OffsetDateTime dataAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(id);

		if (dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}

		if (request.checkNotModified(eTag)) {
			return null;
		}

		FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(id);

		FormaPagamentoDTO formaPagamentoDTO = formaPagamentoDTOAssembler.toModel(formaPagamento);
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(20, TimeUnit.SECONDS)).eTag(eTag)
				.body(formaPagamentoDTO);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

		return formaPagamentoDTOAssembler.toModel(formaPagamentoService.salvar(formaPagamento));
	}

	@PutMapping("/{id}")
	public FormaPagamentoDTO atualizar(@PathVariable Long id,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarPorId(id);

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		return formaPagamentoDTOAssembler.toModel(formaPagamentoService.salvar(formaPagamentoAtual));
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		formaPagamentoService.deletar(formaPagamentoId);
	}

}
