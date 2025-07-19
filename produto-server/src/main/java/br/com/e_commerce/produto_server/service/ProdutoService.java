package br.com.e_commerce.produto_server.service;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;

public interface ProdutoService {
	
	ProdutoRespostaCriacaoDto criaProduto(ProdutoDto produtoDto); 

}
