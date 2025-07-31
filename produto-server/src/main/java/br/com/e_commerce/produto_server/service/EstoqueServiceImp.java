package br.com.e_commerce.produto_server.service;

import org.springframework.stereotype.Service;

import br.com.e_commerce.produto_server.dto.MovimentacaoEstoqueDto;
import br.com.e_commerce.produto_server.entity.Produto;
import br.com.e_commerce.produto_server.entity.TipoMovimentacao;
import br.com.e_commerce.produto_server.exception.CodigoNaoExisteException;
import br.com.e_commerce.produto_server.exception.QuantidadeInsuficienteException;
import br.com.e_commerce.produto_server.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstoqueServiceImp implements EstoqueService {

	private final ProdutoRepository produtoRepository;

	@Override
	public void movimentarEstoque(String codigo, MovimentacaoEstoqueDto movimentacaoEstoqueDto) {

		Produto produto = produtoRepository.findByCodigo(codigo).orElseThrow(CodigoNaoExisteException::new);

		if (movimentacaoEstoqueDto.tipoMovimentacao() == TipoMovimentacao.SAIDA) {
			if (produto.getQuantidadeEstoque() < movimentacaoEstoqueDto.quantidade()) {
				throw new QuantidadeInsuficienteException();
			}
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - movimentacaoEstoqueDto.quantidade());

		} else {
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimentacaoEstoqueDto.quantidade());
		}

		produtoRepository.save(produto);
	}

}
