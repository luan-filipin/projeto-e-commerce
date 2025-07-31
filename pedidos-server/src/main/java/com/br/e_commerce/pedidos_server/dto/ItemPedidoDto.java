package com.br.e_commerce.pedidos_server.dto;

import java.math.BigDecimal;

public record ItemPedidoDto(
		String codigoProduto,
		BigDecimal precoUnitario,
		int quantidade,
		BigDecimal subtotal) {

}
