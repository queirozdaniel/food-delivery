package com.danielqueiroz.fooddelivery.api.utils;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.CidadeController;
import com.danielqueiroz.fooddelivery.api.controller.CozinhaController;
import com.danielqueiroz.fooddelivery.api.controller.EstadoController;
import com.danielqueiroz.fooddelivery.api.controller.EstatisticasController;
import com.danielqueiroz.fooddelivery.api.controller.FluxoPedidoController;
import com.danielqueiroz.fooddelivery.api.controller.FormaPagamentoController;
import com.danielqueiroz.fooddelivery.api.controller.GrupoController;
import com.danielqueiroz.fooddelivery.api.controller.GrupoPermissaoController;
import com.danielqueiroz.fooddelivery.api.controller.PedidoController;
import com.danielqueiroz.fooddelivery.api.controller.PermissaoController;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteController;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteFormaPagamentoController;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteProdutoController;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteProdutoFotoController;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteUsuarioResponsavelController;
import com.danielqueiroz.fooddelivery.api.controller.UsuarioController;
import com.danielqueiroz.fooddelivery.api.controller.UsuarioGrupoController;

@Component
public class CreateLinks {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

	public Link linksToPedidos(String rel) {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));

		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		return new Link(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
	}

	public Link linkToRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).buscarPorId(restauranteId))
				.withRel(rel);
	}

	public Link linkToRestaurante(Long restauranteId) {
		return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String rel) {

		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
				.desassociar(restauranteId, usuarioId)).withRel(rel);
	}

	public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class).associar(restauranteId, null))
				.withRel(rel);
	}

	public Link linkToUsuario(Long usuarioId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(usuarioId))
				.withRel(rel);
	}

	public Link linkToUsuario(Long usuarioId) {
		return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuarios(String rel) {
		return WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel(rel);
	}

	public Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public Link linkToGruposUsuario(Long usuarioId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).buscarTodos(usuarioId))
				.withRel(rel);
	}

	public Link linkToGruposUsuario(Long usuarioId) {
		return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class).buscarTodos(restauranteId))
				.withRel(rel);
	}

	public Link linkToResponsaveisRestaurante(Long restauranteId) {
		return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).buscarPorId(formaPagamentoId, null))
				.withRel(rel);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
				.desassociar(restauranteId, formaPagamentoId)).withRel(rel);
	}

	public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null))
				.withRel(rel);
	}

	public Link linkToCidade(Long cidadeId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscarPorId(cidadeId))
				.withRel(rel);
	}

	public Link linkToCidade(Long cidadeId) {
		return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String rel) {
		return WebMvcLinkBuilder.linkTo(CidadeController.class).withRel(rel);
	}

	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToEstado(Long estadoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscarPorId(estadoId))
				.withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
		return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstados(String rel) {
		return WebMvcLinkBuilder.linkTo(EstadoController.class).withRel(rel);
	}

	public Link linkToEstados() {
		return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId))
				.withRel(rel);
	}
	
	public Link linkToProdutos(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(RestauranteProdutoController.class)
	            .buscarTodos(restauranteId, null)).withRel(rel);
	}

	public Link linkToProdutos(Long restauranteId) {
	    return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}
	

	public Link linkToProduto(Long restauranteId, Long produtoId) {
		return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(RestauranteProdutoFotoController.class)
	            .buscar(restauranteId, produtoId)).withRel(rel);
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
	    return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinhas(String rel) {
		return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
	}

	public Link linkToCozinhas() {
		return linkToCozinhas(IanaLinkRelations.SELF.value());
	}

	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class).confirmar(codigoPedido))
				.withRel(rel);
	}

	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class).entregar(codigoPedido))
				.withRel(rel);
	}

	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class).cancelar(codigoPedido))
				.withRel(rel);
	}

	public Link linkToRestaurantes(String rel) {
		String restaurantesUrl = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();

		return new Link(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
	}

	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class).buscarTodos(restauranteId))
				.withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class).buscarPorId(cozinhaId))
				.withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId) {
		return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).abrir(restauranteId))
				.withRel(rel);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).fechar(restauranteId))
				.withRel(rel);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).inativar(restauranteId))
				.withRel(rel);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).ativar(restauranteId))
				.withRel(rel);
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
		return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFormasPagamento(String rel) {
		return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel(rel);
	}

	public Link linkToFormasPagamento() {
		return linkToFormasPagamento(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGrupos(String rel) {
	    return WebMvcLinkBuilder.linkTo(GrupoController.class).withRel(rel);
	}

	public Link linkToGrupos() {
	    return linkToGrupos(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(GrupoPermissaoController.class)
	            .buscarTodos(grupoId)).withRel(rel);
	}  

	public Link linkToPermissoes(String rel) {
	    return WebMvcLinkBuilder.linkTo(PermissaoController.class).withRel(rel);
	}

	public Link linkToPermissoes() {
	    return linkToPermissoes(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId) {
	    return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(GrupoPermissaoController.class)
	            .associar(grupoId, null)).withRel(rel);
	}

	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(GrupoPermissaoController.class)
	            .desassociar(grupoId, permissaoId)).withRel(rel);
	}
	
	public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(UsuarioGrupoController.class)
	            .associar(usuarioId, null)).withRel(rel);
	}

	public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(UsuarioGrupoController.class)
	            .desassociar(usuarioId, grupoId)).withRel(rel);
	} 
	
	public Link linkToPedidos(String rel) {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosUrl, 
				PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
	}
	
	public Link linkToEstatisticas(String rel) {
	    return WebMvcLinkBuilder.linkTo(EstatisticasController.class).withRel(rel);
	}

	public Link linkToEstatisticasVendasDiarias(String rel) {
	    TemplateVariables filtroVariables = new TemplateVariables(
	            new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
	            new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
	    
	    String pedidosUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
	    		.methodOn(EstatisticasController.class)
	            .consultarVendasDiarias(null, null)).toUri().toString();
	    
	    return new Link(UriTemplate.of(pedidosUrl, filtroVariables), rel);
	}  
	
}
