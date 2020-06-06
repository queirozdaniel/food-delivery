package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import com.danielqueiroz.fooddelivery.core.data.PageableTranslator;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.exception.NegocioException;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.repository.PedidoRepository;
import com.danielqueiroz.fooddelivery.domain.repository.filter.PedidoFilter;
import com.danielqueiroz.fooddelivery.domain.service.PedidoService;
import com.danielqueiroz.fooddelivery.infrastructure.repository.spec.PedidoSpecFactory;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

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
	
	@GetMapping
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
		
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPages = pedidoRepository.findAll(PedidoSpecFactory.usandoFiltro(filtro), pageable);
		List<PedidoResumoDTO> pedidosDTO = pedidoResumoDtoAssembler.toCollectionModel(pedidosPages.getContent());
		Page<PedidoResumoDTO> pedidosDTOPages = new PageImpl<>(pedidosDTO, pageable, pedidosPages.getTotalElements());
	
		return pedidosDTOPages;
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscarPedido(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);
		
		return pedidoDtoAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = pedidoService.emitir(novoPedido);

	        return pedidoDtoAssembler.toModel(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = ImmutableMap.of(
					"codigo", "codigo",
					"nomeCliente", "cliente.nome",
					"restaurante.nome", "restaurante.nome",
					"valorTotal", "valorTotal"
				);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	
}
