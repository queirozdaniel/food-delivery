package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.FormaPagamentoController;
import com.danielqueiroz.fooddelivery.api.model.FormaPagamentoDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

	@Autowired
    private ModelMapper modelMapper;
    
	@Autowired
    private CreateLinks createLinks;
    
	@Autowired
	private UserSecurity userSecurity;
	
    public FormaPagamentoDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }
    
    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
    	FormaPagamentoDTO formaPagamentoModel = 
                createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModel);

        if (userSecurity.podeConsultarFormasPagamento()) {
        	formaPagamentoModel.add(createLinks.linkToFormasPagamento("formasPagamento"));
		}
        
        return formaPagamentoModel;
    }
    
    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
    	CollectionModel<FormaPagamentoDTO> collectionModel = super.toCollectionModel(entities);
        if (userSecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(createLinks.linkToFormasPagamento());
        }
        return collectionModel;
    }  
	
}
