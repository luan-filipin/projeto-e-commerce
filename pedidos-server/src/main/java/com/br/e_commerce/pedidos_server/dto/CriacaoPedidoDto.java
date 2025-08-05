package com.br.e_commerce.pedidos_server.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.br.e_commerce.pedidos_server.entity.StatusPedido;

public record CriacaoPedidoDto(
		Long id,
		StatusPedido status,
		LocalDateTime dataCriacao,
		String usuarioLogin,
		BigDecimal Total,
		List<CriacaoItemPedidoDto> itens) {

}
