package com.danielqueiroz.fooddelivery.api.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.PedidoDTO;
import com.danielqueiroz.fooddelivery.api.model.PedidoResumoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.PedidoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.PedidoInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.PedidoResumoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.input.PedidoInput;
import com.danielqueiroz.fooddelivery.api.openapi.controller.PedidoControllerOpenApi;
import com.danielqueiroz.fooddelivery.core.data.PageWrapper;
import com.danielqueiroz.fooddelivery.core.data.PageableTranslator;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.exception.NegocioException;
import com.danielqueiroz.fooddelivery.domain.filter.PedidoFilter;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.repository.PedidoRepository;
import com.danielqueiroz.fooddelivery.domain.service.PedidoService;
import com.danielqueiroz.fooddelivery.infrastructure.repository.spec.PedidoSpecFactory;

@RestController
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoDTOAssembler pedidoDtoAssembler;

	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoDtoAssembler;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private UserSecurity userSecurity;

	@CheckSecurity.Pedidos.PodePesquisar
	@Override
	@GetMapping
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {

		Pageable pageableTraduzido = traduzirPageable(pageable);

		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecFactory.usandoFiltro(filtro), pageableTraduzido);

		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		PagedModel<PedidoResumoDTO> pedidosPagedModel = pagedResourcesAssembler.toModel(pedidosPage,
				pedidoResumoDtoAssembler);

		return pedidosPagedModel;
	}

	@CheckSecurity.Pedidos.PodeBuscar
	@Override
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscarPedido(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);

		return pedidoDtoAssembler.toModel(pedido);
	}

	@CheckSecurity.Pedidos.PodeCriar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(userSecurity.getUsuarioId());

			novoPedido = pedidoService.emitir(novoPedido);

			return pedidoDtoAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of("codigo", "codigo", "subtotal", "subtotal", "taxaFrete", "taxaFrete", "valorTotal",
				"valorTotal", "dataCriacao", "dataCriacao", "restaurante.nome", "restaurante.nome", "restaurante.id",
				"restaurante.id", "cliente.id", "cliente.id", "cliente.nome", "cliente.nome");

		return PageableTranslator.translate(apiPageable, mapeamento);
	}

}
