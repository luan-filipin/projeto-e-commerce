package com.br.e_commerce.pedidos_server.dto;

import com.br.e_commerce.pedidos_server.entity.TipoMovimentacao;

public record MovimentacaoEstoqueDto(
		Integer quantidade, 
		TipoMovimentacao tipoMovimentacao) {

}
