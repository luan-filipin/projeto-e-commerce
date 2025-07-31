package com.br.e_commerce.pedidos_server.dto;

import java.math.BigDecimal;
import java.util.List;

import com.br.e_commerce.pedidos_server.entity.StatusPedido;

public record CriacaoPedidosDto(
		Long id,
		String codigo,
		StatusPedido status,
		BigDecimal total,
		List<CriacaoItemPedidoDto> itens) {

}
