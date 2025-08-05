package com.br.e_commerce.pedidos_server.dto;

import java.util.List;

public record PedidoDto(
		String usuarioLogin,
		List<ItemPedidoDto> itens) {

}
