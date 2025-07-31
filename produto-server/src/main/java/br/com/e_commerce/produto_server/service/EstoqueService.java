package br.com.e_commerce.produto_server.service;

import br.com.e_commerce.produto_server.dto.MovimentacaoEstoqueDto;

public interface EstoqueService {

	void movimentarEstoque(String codigo, MovimentacaoEstoqueDto movimentacaoEstoqueDto);
}
