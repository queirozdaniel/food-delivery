package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.controller.PedidoController;
import com.danielqueiroz.fooddelivery.api.model.PedidoDTO;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CreateLinks pedidoLinks;

	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}

	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoDto);

		pedidoDto.add(pedidoLinks.linksToPedidos("pedidos"));
		
		pedidoDto.getRestaurante().add(pedidoLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		

		pedidoDto.getCliente()
				.add(pedidoLinks.linkToUsuario(pedido.getCliente().getId()));

		pedidoDto.getFormaPagamento()
				.add(pedidoLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

		pedidoDto.getEnderecoEntrega().getCidade()
				.add(pedidoLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

		
		if (pedido.podeSerConfirmado()) {
			pedidoDto.add(pedidoLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		}

		if (pedido.podeSerCancelado()) {
			pedidoDto.add(pedidoLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		}

		
		if (pedido.podeSerEntregue()) {
			pedidoDto.add(pedidoLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		}

		pedidoDto.getItens().forEach(item -> {
			item.add(pedidoLinks.linkToProduto(
	                pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});

		return pedidoDto;
	}

}
