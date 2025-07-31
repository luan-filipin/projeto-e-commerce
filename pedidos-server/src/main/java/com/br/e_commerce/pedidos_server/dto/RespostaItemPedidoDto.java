package com.br.e_commerce.pedidos_server.dto;

import java.math.BigDecimal;

public record RespostaItemPedidoDto(
		Long id, 
		String codigoProduto,
		String nomeProduto,
		int quantidade,
		BigDecimal precoUnitario,
		BigDecimal subTotal) {

}
