package com.danielqueiroz.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.exception.NegocioException;
import com.danielqueiroz.fooddelivery.domain.exception.PedidoNaoEncontradoException;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;
import com.danielqueiroz.fooddelivery.domain.model.FormaPagamento;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;
import com.danielqueiroz.fooddelivery.domain.model.Produto;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	
	public Pedido buscarPorCodigo(String codigo) {
		return pedidoRepository.findByCodigo(codigo).orElseThrow( () -> new PedidoNaoEncontradoException(codigo));
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
	    validarPedido(pedido);
	    validarItens(pedido);

	    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
	    pedido.calcularValorTotal();

	    return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido) {
		System.out.println(pedido.getEnderecoEntrega().getCidade().getId());
		System.out.println(pedido.getCliente().getId());
		System.out.println(pedido.getRestaurante().getId());
		System.out.println(pedido.getFormaPagamento().getId());
	    Cidade cidade = cidadeService.buscarPorId(pedido.getEnderecoEntrega().getCidade().getId());
	    Usuario cliente = usuarioService.buscarPorId(pedido.getCliente().getId());
	    Restaurante restaurante = restauranteService.buscarPorId(pedido.getRestaurante().getId());
	    FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(pedido.getFormaPagamento().getId());

	    pedido.getEnderecoEntrega().setCidade(cidade);
	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.setFormaPagamento(formaPagamento);
	    System.out.println("Passou");
	    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
	        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	                formaPagamento.getDescricao()));
	    }
	}

	private void validarItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = produtoService.buscarPorId(
	                pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}
	
}
