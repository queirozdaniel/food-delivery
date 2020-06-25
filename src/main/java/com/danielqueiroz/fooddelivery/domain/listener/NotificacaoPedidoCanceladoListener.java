package com.danielqueiroz.fooddelivery.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.danielqueiroz.fooddelivery.domain.event.PedidoCanceladoEvent;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;
import com.danielqueiroz.fooddelivery.domain.service.EnvioEmailService;
import com.danielqueiroz.fooddelivery.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoPedidoCanceladoListener {

	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("pedido-cancelado.html")
				.destinatario(pedido.getCliente().getEmail())
				.variavel("pedido", pedido)
				.build();
		
		envioEmail.enviar(mensagem);
	}
	
}
