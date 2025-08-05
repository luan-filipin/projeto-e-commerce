package com.br.e_commerce.pedidos_server.dto.kafka;

public record ItemPedidoEvent(
		String codigoProduto,
		Integer quantidade) {

}
