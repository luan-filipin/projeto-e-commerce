package br.com.e_commerce.produto_server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.entity.Produto;
import br.com.e_commerce.produto_server.exception.CodigoJaExisteException;
import br.com.e_commerce.produto_server.exception.CodigoNaoExisteException;
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

	@DisplayName("Deve criar um produto com sucesso.")
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
	
	@DisplayName("Deve falahar ao tentar criar um produto com o codigo ja existente.")
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

		CodigoJaExisteException exception = assertThrows(CodigoJaExisteException.class, () -> {
			produtoServiceImp.criaProduto(produtoEntrada);
		});

		assertEquals("Este codigo ja esta cadastrado!", exception.getMessage());

		verify(produtoRepository, never()).save(any());
	}
	
	@DisplayName("Deve retornar o produto pesquisado pelo codigo com sucesso.")
	@Test
	public void deveProcuraProdutoPeloCodigoComSucesso() {
		// Arrange
		String codigo = "1";
		String nome = "Mouse Gamer RGB";
		String descricao = "Mouse ergonômico com iluminação RGB e 6 botões programáveis.";
		BigDecimal preco = new BigDecimal("149.90");
		Integer quantidade = 120;
		String categoria = "Periféricos";
		String imagemUrl = "https://minha-loja.com/imagens/produtos/mouse-gamer-rgb.jpg";

		ProdutoDto produtoDto = new ProdutoDto(codigo, nome, descricao, preco, quantidade, categoria, imagemUrl, true);

		Produto produtoEntity = new Produto(1L, codigo, nome, descricao, preco, quantidade, categoria, imagemUrl, null,
				null, true);

		when(produtoRepository.findByCodigo(codigo)).thenReturn(Optional.of(produtoEntity));
		when(produtoMapper.toDto(produtoEntity)).thenReturn(produtoDto);

		// Act & Assert
		ProdutoDto result = produtoServiceImp.procuraProdutoPeloCodigo(codigo);

		assertNotNull(result);
		assertEquals(produtoDto, result);
		assertEquals(produtoDto.codigo(), result.codigo());
		assertEquals(produtoDto.nome(), result.nome());

		verify(produtoRepository).findByCodigo(codigo);
		verify(produtoMapper).toDto(produtoEntity);
	}
	
	@DisplayName("Deve falahar ao produto um produto pelo codigo.")
	@Test
	public void deveFalharAoProcurarProdutoPeloCodigo() {

		String codigo = "0";

		when(produtoRepository.findByCodigo(codigo)).thenReturn(Optional.empty());

		CodigoNaoExisteException exception = assertThrows(CodigoNaoExisteException.class, () -> {
			produtoServiceImp.procuraProdutoPeloCodigo(codigo);
		});

		assertEquals("O codigo do produto não existe!", exception.getMessage());

		verify(produtoMapper, never()).toDto(any());

	}
	
	@DisplayName("Deve retornar todos os produtos do banco paginados")
	@Test
	public void deveRetornarTodosOsProdutos() {

		Pageable pageable = PageRequest.of(0, 2);
		LocalDateTime dataFixa = LocalDateTime.of(2024, 1, 1, 12, 0);

		Produto produto1 = new Produto(1L, "PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", dataFixa,
				dataFixa, true);

		Produto produto2 = new Produto(1L, "PROD98831", "Fone Bluetooth teste",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", dataFixa,
				dataFixa, true);

		List<Produto> listaProduto = List.of(produto1, produto2);
		Page<Produto> paginaProduto = new PageImpl<>(listaProduto);

		ProdutoDto produtoDto1 = new ProdutoDto("PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		ProdutoDto produtoDto2 = new ProdutoDto("PROD98831", "Fone Bluetooth teste",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		when(produtoRepository.findAll(pageable)).thenReturn(paginaProduto);
		when(produtoMapper.toDto(produto1)).thenReturn(produtoDto1);
		when(produtoMapper.toDto(produto2)).thenReturn(produtoDto2);
		
		Page<ProdutoDto> resultado = produtoServiceImp.retornaTodosOsprodutos(pageable);
		
		assertNotNull(resultado);
		assertEquals(2, resultado.getContent().size());
		assertEquals("Fone Bluetooth X500", resultado.getContent().get(0).nome());
		assertEquals("Fone Bluetooth teste", resultado.getContent().get(1).nome());
		
		verify(produtoRepository).findAll(pageable);
		verify(produtoMapper).toDto(produto1);
		verify(produtoMapper).toDto(produto2);
	}
	@DisplayName("Deve retornar uma lista vazia quando nao tem produto")
	@Test
	public void deveRetornarPaginaVaziaQuandoNaoExistemProdutos() {
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<Produto> paginaVazia = new PageImpl<>(Collections.emptyList());
		
		when(produtoRepository.findAll(pageable)).thenReturn(paginaVazia);
		
		Page<ProdutoDto> resultado = produtoServiceImp.retornaTodosOsprodutos(pageable);
		
		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		
		verify(produtoRepository, times(1)).findAll(pageable);
		
	}
	
	@DisplayName("Deve deletar o produto pelo codigo")
	@Test
	public void deveDeletarOProdutopeloCodigo() {
		
		String codigo = "111111";
		
		LocalDateTime dataFixa = LocalDateTime.of(2024, 1, 1, 12, 0);

		Produto produto1 = new Produto(1L, "PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", dataFixa,
				dataFixa, true);
		
		when(produtoRepository.findByCodigo(codigo)).thenReturn(Optional.of(produto1));
		
		produtoServiceImp.deletaProdutoPeloCodigo(codigo);
		
		verify(produtoRepository).findByCodigo(codigo);
		verify(produtoRepository).delete(produto1);	
	}
	
	@DisplayName("Deve lançar exceção ao tentar deletar produto com código inexistente")
	@Test
	public void deveFalharAoTentarDeletarOProduto() {
		
		String codigoInexistente = "00000";
		
		when(produtoRepository.findByCodigo(codigoInexistente)).thenReturn(Optional.empty());
		
		CodigoNaoExisteException exception = assertThrows(CodigoNaoExisteException.class, () -> {
			produtoServiceImp.deletaProdutoPeloCodigo(codigoInexistente);
		});
		
		assertEquals("O codigo do produto não existe!", exception.getMessage());

		verify(produtoRepository, never()).delete(any());
	}
}
