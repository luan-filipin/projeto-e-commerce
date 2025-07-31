package com.br.e_commerce.pedidos_server.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RespostaPedidoDto(
	Long id, 
	Long clienteId,
	LocalDateTime dataCriacao,
	BigDecimal valorTotal,
	String status,
	List<RespostaItemPedidoDto> itens) {

	
	
}
