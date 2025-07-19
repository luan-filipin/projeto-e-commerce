package br.com.e_commerce.produto_server.service;

import org.springframework.stereotype.Service;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.entity.Produto;
import br.com.e_commerce.produto_server.mapper.ProdutoMapper;
import br.com.e_commerce.produto_server.mapper.ProdutoRespostaCriacaoMapper;
import br.com.e_commerce.produto_server.repository.ProdutoRepository;
import br.com.e_commerce.produto_server.service.validador.ProdutoValidador;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdutoServiceImp implements ProdutoService{
	
	private final ProdutoRepository produtoRepository;
	private final ProdutoValidador produtoValidador;
	private final ProdutoMapper produtoMapper;
	private final ProdutoRespostaCriacaoMapper produtoRespostaCriacaoMapper;
	

	@Override
	public ProdutoRespostaCriacaoDto criaProduto(ProdutoDto produtoDto) {
		produtoValidador.validaSeCodigoJaFoiUsado(produtoDto.codigo());
		Produto produtoSalvo = produtoRepository.save(produtoMapper.toEntity(produtoDto));
		return produtoRespostaCriacaoMapper.toDto(produtoSalvo);
	}

}
