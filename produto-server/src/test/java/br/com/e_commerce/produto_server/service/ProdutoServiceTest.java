package br.com.e_commerce.produto_server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.entity.Produto;
import br.com.e_commerce.produto_server.exception.CodigoJaExisteException;
import br.com.e_commerce.produto_server.mapper.ProdutoMapper;
import br.com.e_commerce.produto_server.mapper.ProdutoRespostaCriacaoMapper;
import br.com.e_commerce.produto_server.repository.ProdutoRepository;
import br.com.e_commerce.produto_server.service.validador.ProdutoValidador;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

	@Mock
	private ProdutoRepository produtoRepository;
	@Mock
	private ProdutoMapper produtoMapper;
	@Mock
	private ProdutoRespostaCriacaoMapper produtoRespostaCriacaoMapper;
	@Mock
	private ProdutoValidador produtoValidador;
	@InjectMocks
	private ProdutoServiceImp produtoServiceImp;

	@Test
	public void deveCriarProdutoComSucesso() {

		String codigo = "1";
		String nome = "Mouse Gamer RGB";
		String descricao = "Mouse ergonômico com iluminação RGB e 6 botões programáveis.";
		BigDecimal preco = new BigDecimal("149.90");
		Integer quantidade = 120;
		String categoria = "Periféricos";
		String imagemUrl = "https://minha-loja.com/imagens/produtos/mouse-gamer-rgb.jpg";

		ProdutoDto produtoEntrada = new ProdutoDto(codigo, nome, descricao, preco, quantidade, categoria, imagemUrl,
				true);

		Produto produtoEntity = new Produto(1L, codigo, nome, descricao, preco, quantidade, categoria, imagemUrl, null,
				null, true);

		Produto produtoSalvo = new Produto(1L, codigo, nome, descricao, preco, quantidade, categoria, imagemUrl, null,
				null, true);

		ProdutoRespostaCriacaoDto produtoRetorno = new ProdutoRespostaCriacaoDto(1L, "Mouse Gamer RGB", null);

		doNothing().when(produtoValidador).validaSeCodigoJaFoiUsado("1");
		when(produtoMapper.toEntity(produtoEntrada)).thenReturn(produtoEntity);
		when(produtoRepository.save(produtoEntity)).thenReturn(produtoSalvo);
		when(produtoRespostaCriacaoMapper.toDto(produtoSalvo)).thenReturn(produtoRetorno);

		ProdutoRespostaCriacaoDto result = produtoServiceImp.criaProduto(produtoEntrada);

		assertNotNull(result);
		assertEquals(1L, result.id());
		assertNull(result.dataCriacao());
		assertEquals(produtoEntrada.nome(), result.nome());

		verify(produtoValidador).validaSeCodigoJaFoiUsado("1");
		verify(produtoMapper).toEntity(produtoEntrada);
		verify(produtoRepository).save(produtoEntity);
		verify(produtoRespostaCriacaoMapper).toDto(produtoSalvo);

	}
	
	@Test
	public void deveFalharAoCriarUmProduto() {
		
		String codigo = "0";
		String nome = "Mouse Gamer RGB";
		String descricao = "Mouse ergonômico com iluminação RGB e 6 botões programáveis.";
		BigDecimal preco = new BigDecimal("149.90");
		Integer quantidade = 120;
		String categoria = "Periféricos";
		String imagemUrl = "https://minha-loja.com/imagens/produtos/mouse-gamer-rgb.jpg";

		
		ProdutoDto produtoEntrada = new ProdutoDto(codigo, nome, descricao, preco, quantidade, categoria, imagemUrl,
				true);
		
		doThrow(new CodigoJaExisteException()).when(produtoValidador).validaSeCodigoJaFoiUsado(codigo);
		
		CodigoJaExisteException exception = assertThrows(CodigoJaExisteException.class, ()->{
			produtoServiceImp.criaProduto(produtoEntrada);
		});
		
		assertEquals("Este codigo ja esta cadastrado!", exception.getMessage());
		
		verify(produtoRepository, never()).save(any());
	}
}
