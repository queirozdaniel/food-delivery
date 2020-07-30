package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.PedidoController;
import com.danielqueiroz.fooddelivery.api.model.PedidoDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CreateLinks createLinks;

	@Autowired
	private UserSecurity userSecurity;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}

	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoDto);

		pedidoDto.add(createLinks.linksToPedidos("pedidos"));
		
		pedidoDto.getRestaurante().add(createLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		

		pedidoDto.getCliente()
				.add(createLinks.linkToUsuario(pedido.getCliente().getId()));

		pedidoDto.getFormaPagamento()
				.add(createLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

		pedidoDto.getEnderecoEntrega().getCidade()
				.add(createLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

		if (userSecurity.podeGerenciarPedidos(pedidoDto.getCodigo())) {
			if (pedido.podeSerConfirmado()) {
				pedidoDto.add(createLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}
			
			if (pedido.podeSerCancelado()) {
				pedidoDto.add(createLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}
			
			
			if (pedido.podeSerEntregue()) {
				pedidoDto.add(createLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}
		}
		

		pedidoDto.getItens().forEach(item -> {
			item.add(createLinks.linkToProduto(
	                pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});

		return pedidoDto;
	}

}
