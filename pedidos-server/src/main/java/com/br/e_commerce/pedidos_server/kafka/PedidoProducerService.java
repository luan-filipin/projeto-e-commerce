package com.br.e_commerce.pedidos_server.kafka;

import org.springframework.stereotype.Service;

import com.br.e_commerce.pedidos_server.dto.kafka.PedidoCriadoEvent;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Service
public class PedidoProducerService {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	@Value("${topic.name.pedido-criado}")
	private String topicoPedidoCriado;
	
	public void enviarPedidoCriado(PedidoCriadoEvent evento) {
		kafkaTemplate.send(topicoPedidoCriado, evento);
	}
}
