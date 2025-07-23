package br.com.e_commerce.produto_server.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;

public interface ProdutoService {
	
	ProdutoRespostaCriacaoDto criaProduto(ProdutoDto produtoDto); 

	ProdutoDto procuraProdutoPeloCodigo(String codigo);
	
	Page<ProdutoDto> retornaTodosOsprodutos(Pageable pageable);
}
