package br.com.e_commerce.produto_server.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.entity.Produto;
import br.com.e_commerce.produto_server.exception.CodigoDoProdutoInvalidoException;
import br.com.e_commerce.produto_server.exception.CodigoJaExisteException;
import br.com.e_commerce.produto_server.exception.CodigoNaoExisteException;
import br.com.e_commerce.produto_server.mapper.ProdutoMapper;
import br.com.e_commerce.produto_server.mapper.ProdutoRespostaCriacaoMapper;
import br.com.e_commerce.produto_server.repository.ProdutoRepository;
import br.com.e_commerce.produto_server.service.validador.ProdutoValidador;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdutoServiceImp implements ProdutoService {

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

	@Override
	public List<ProdutoDto> criaProdutosEmLote(List<ProdutoDto> produtosDto) {

		// Extrai todos os codigo enviados.
		List<String> codigos = produtosDto.stream().map(ProdutoDto::codigo).toList();

		// Verificar no banco quais desses códigos já existem.
		List<String> codigosJaExistentes = produtoRepository.findCodigosExistentes(codigos);

		// Se houver duplicado lança a exception.
		if (!codigosJaExistentes.isEmpty()) {
			throw new CodigoJaExisteException("Os seguintes códigos já existem: " + codigosJaExistentes);
		}

		List<Produto> entidades = produtoMapper.toEntityList(produtosDto);

		List<Produto> salvos = produtoRepository.saveAll(entidades);

		return produtoMapper.toDtoList(salvos);
	}

	@Override
	public ProdutoDto procuraProdutoPeloCodigo(String codigo) {
		Produto produtoEntity = produtoRepository.findByCodigo(codigo).orElseThrow(CodigoNaoExisteException::new);
		return produtoMapper.toDto(produtoEntity);
	}

	@Override
	public Page<ProdutoDto> retornaTodosOsprodutos(Pageable pageable) {
		Page<Produto> todosProdutos = produtoRepository.findAll(pageable);
		return todosProdutos.map(produtoMapper::toDto);
	}

	@Override
	@Transactional
	public void deletaProdutoPeloCodigo(String codigo) {
		Produto produto = produtoRepository.findByCodigo(codigo).orElseThrow(CodigoNaoExisteException::new);
		produtoRepository.delete(produto);
	}

	@Override
	public ProdutoDto atualizaProdutoPeloCodigo(String codigo, ProdutoDto produtoDto) {
		Produto produto = produtoRepository.findByCodigo(codigo).orElseThrow(CodigoNaoExisteException::new);

		if (!codigo.equals(produtoDto.codigo())) {
			throw new CodigoDoProdutoInvalidoException();
		}

		produtoMapper.atualizaDoDto(produtoDto, produto);
		Produto produtoSalvo = produtoRepository.save(produto);
		return produtoMapper.toDto(produtoSalvo);
	}

}
