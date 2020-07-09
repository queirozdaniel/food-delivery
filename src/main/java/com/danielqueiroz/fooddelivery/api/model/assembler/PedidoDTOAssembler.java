package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.CidadeController;
import com.danielqueiroz.fooddelivery.api.controller.FormaPagamentoController;
import com.danielqueiroz.fooddelivery.api.controller.PedidoController;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteController;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteProdutoController;
import com.danielqueiroz.fooddelivery.api.controller.UsuarioController;
import com.danielqueiroz.fooddelivery.api.model.PedidoDTO;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}

	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoDto);

		pedidoDto.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));

		pedidoDto.getRestaurante().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.buscarPorId(pedido.getRestaurante().getId()))
				.withSelfRel());

		pedidoDto.getCliente()
				.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
						.buscar(pedido.getCliente().getId()))
						.withSelfRel());

		// Passamos null no segundo argumento, porque é indiferente para a
		// construção da URL do recurso de forma de pagamento
		pedidoDto.getFormaPagamento()
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(FormaPagamentoController.class)
						.buscar(pedido.getFormaPagamento().getId(), null))
						.withSelfRel());

		pedidoDto.getEnderecoEntrega().getCidade()
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(CidadeController.class)
						.buscarPorId(pedido.getEnderecoEntrega().getCidade().getId()))
						.withSelfRel());

		pedidoDto.getItens().forEach(item -> {
			item.add(WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
									.buscar(pedidoDto.getRestaurante().getId(), item.getProdutoId()))
							.withRel("produto"));
		});

		return pedidoDto;
	}

}
