package com.br.e_commerce.pedidos_server.dto;

import java.math.BigDecimal;

public record CriacaoItemPedidoDto(
	    Long id,
	    String codigoProduto,
	    String nomeProduto,
	    BigDecimal precoUnitario,
	    Integer quantidade,
	    BigDecimal subtotal) {

}
