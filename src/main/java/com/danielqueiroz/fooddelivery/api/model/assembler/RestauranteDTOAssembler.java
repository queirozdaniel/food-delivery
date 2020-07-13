package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteController;
import com.danielqueiroz.fooddelivery.api.model.RestauranteDTO;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CreateLinks createLinks;
	
	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}
	
	@Override
    public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(createLinks.linkToRestaurantes("restaurantes"));
        
        restauranteModel.getCozinha().add(
        		createLinks.linkToCozinha(restaurante.getCozinha().getId()));
        
        if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null ) {
        	restauranteModel.getEndereco().getCidade().add(
        			createLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		}
        
        restauranteModel.add(createLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
                "formas-pagamento"));
        
        restauranteModel.add(createLinks.linkToResponsaveisRestaurante(restaurante.getId(), 
                "responsaveis"));
        
        restauranteModel.add(createLinks.linkToProdutos(restaurante.getId(), "produtos"));
        
        if (restaurante.ativacaoPermitida()) {
        	restauranteModel.add(
        			createLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
        	restauranteModel.add(
        			createLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
        	restauranteModel.add(
        			createLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
        	restauranteModel.add(
        			createLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(createLinks.linkToRestaurantes());
    } 
	
}
