package br.com.e_commerce.produto_server.kafka;

import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.e_commerce.produto_server.dto.kafka.ItemPedidoEvent;
import br.com.e_commerce.produto_server.dto.kafka.PedidoCriadoEvent;
import br.com.e_commerce.produto_server.service.ProdutoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PedidoConsumer {

	private final ProdutoService produtoService;
	Logger logger = Logger.getLogger(getClass().getName());
	
	@KafkaListener(topics = "pedido-criado", groupId = "produto-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumirPedidoCriado(PedidoCriadoEvent event) {
        logger.info("Evento pedido recebido, id: " + event.pedidoId());
        
        for(ItemPedidoEvent item: event.itens()) {
        	produtoService.baixarEstoque(item.codigoProduto(), item.quantidade());
        }
        
	}
}
	
