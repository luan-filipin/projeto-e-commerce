package br.com.e_commerce.produto_server.dto;

import br.com.e_commerce.produto_server.entity.TipoMovimentacao;

public record MovimentacaoEstoqueDto(
		Integer quantidade,
		TipoMovimentacao tipoMovimentacao ) {

}
