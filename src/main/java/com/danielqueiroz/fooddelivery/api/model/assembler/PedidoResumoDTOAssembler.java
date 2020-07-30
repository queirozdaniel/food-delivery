package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.PedidoController;
import com.danielqueiroz.fooddelivery.api.model.PedidoResumoDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CreateLinks createLinks;
	
	@Autowired
	private UserSecurity userSecurity;

	public PedidoResumoDTOAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

    
	@Override
    public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDto);
        
        if (userSecurity.podePesquisarPedidos()) {
        	pedidoDto.add(createLinks.linkToPedidos("pedidos"));
        }
        
        if (userSecurity.podeConsultarRestaurantes()) {
        	pedidoDto.getRestaurante().add(
        			createLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (userSecurity.podeConsultarUsuariosGruposPermissoes()) {
        	pedidoDto.getCliente().add(createLinks.linkToUsuario(pedido.getCliente().getId()));
        }
        
        return pedidoDto;
    }
    
}
