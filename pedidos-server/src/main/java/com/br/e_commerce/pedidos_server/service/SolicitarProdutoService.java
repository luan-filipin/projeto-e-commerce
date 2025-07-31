package com.br.e_commerce.pedidos_server.service;

import com.br.e_commerce.pedidos_server.dto.ProdutoDto;
import com.br.e_commerce.pedidos_server.entity.TipoMovimentacao;

public interface SolicitarProdutoService {
	
	ProdutoDto buscarProdutoPeloCodigo(String codigo);
	
	void movimentarEstoque(String codigo, Integer quantidade, TipoMovimentacao tipo);

}
