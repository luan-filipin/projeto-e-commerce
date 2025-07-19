package br.com.e_commerce.produto_server.service.validador;

import org.springframework.stereotype.Component;

import br.com.e_commerce.produto_server.exception.CodigoJaExisteException;
import br.com.e_commerce.produto_server.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProdutoValidador {
	
	private final ProdutoRepository produtoRepository;
	
	public void validaSeCodigoJaFoiUsado(String codigo) {
		if(produtoRepository.existsByCodigo(codigo)) {
			throw new CodigoJaExisteException();
		}
	}

}
