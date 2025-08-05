package br.com.e_commerce.produto_server.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;

public interface ProdutoService {
	
	ProdutoRespostaCriacaoDto criaProduto(ProdutoDto produtoDto); 
	
	List<ProdutoDto> criaProdutosEmLote(List<ProdutoDto> produtoDto);

	ProdutoDto procuraProdutoPeloCodigo(String codigo);
	
	Page<ProdutoDto> retornaTodosOsprodutos(Pageable pageable);
	
	void deletaProdutoPeloCodigo(String codigo);
	
	ProdutoDto atualizaProdutoPeloCodigo(String codigo, ProdutoDto produtDto);
	
	void baixarEstoque(String codigoProduto, Integer quantidade);
}
