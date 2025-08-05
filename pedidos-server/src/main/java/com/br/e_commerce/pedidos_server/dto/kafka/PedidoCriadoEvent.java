package com.br.e_commerce.pedidos_server.dto.kafka;

import java.util.List;

public record PedidoCriadoEvent(
		Long pedidoId,
		List<ItemPedidoEvent> itens) {

}
