package br.com.e_commerce.produto_server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.e_commerce.produto_server.config.GlobalExceptionHandler;
import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.exception.CodigoJaExisteException;
import br.com.e_commerce.produto_server.exception.CodigoNaoExisteException;
import br.com.e_commerce.produto_server.service.ProdutoService;

@WebMvcTest(ProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
public class ProdutoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private ProdutoService produtoService;

	@DisplayName("Cria um produto se as informações forem validas.")
	@Test
	public void deveCriarProdutoQuandoARequisicaoForValida() throws Exception {

		ProdutoDto produtoDto = new ProdutoDto("PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		LocalDateTime dataFixa = LocalDateTime.of(2024, 1, 1, 12, 0);
		ProdutoRespostaCriacaoDto produtoSalvo = new ProdutoRespostaCriacaoDto(1L, "Fone Bluetooth X500", dataFixa);

		when(produtoService.criaProduto(produtoDto)).thenReturn(produtoSalvo);

		mockMvc.perform(post("/api/produtos").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(produtoDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome").value("Fone Bluetooth X500"));
	}

	@DisplayName("Retornar erro se as informações forem invalidas.")
	@Test
	public void deveRetornarErro400QuandoDadosObrigatoriosForemInvalidos() throws Exception {

		ProdutoDto produtoInvalido = new ProdutoDto("PROD001", "", "Descrição válida", new BigDecimal("99.90"), 10,
				"Categoria válida", "https://exemplo.com/imagem.jpg", true);

		mockMvc.perform(post("/api/produtos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(produtoInvalido)))
		.andExpect(status().isBadRequest());
	}
	
	@DisplayName("Cria varios produtos com sucesso.")
	@Test
	public void deveCriarProdutosEmLoteComSucesso() throws Exception{
		
		ProdutoDto produto1 = new ProdutoDto("PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		ProdutoDto produto2 = new ProdutoDto("PROD98831", "Fone Bluetooth teste",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		List<ProdutoDto> entrada = List.of(produto1, produto2);
		
		when(produtoService.criaProdutosEmLote(entrada)).thenReturn(entrada);
		
		mockMvc.perform(post("/api/produtos/lote")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(entrada)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.length()").value(2))
		.andExpect(jsonPath("$[0].nome").value("Fone Bluetooth X500"))
		.andExpect(jsonPath("$[1].nome").value("Fone Bluetooth teste"));
			
	}
	
	@DisplayName("Deve retornar erro ao tentar criar produtos com códigos já existentes.")
	@Test
	public void deveRetornarErroSeAsInformacoesForemInvalidas() throws Exception{
		
		ProdutoDto produto1 = new ProdutoDto("PROD123", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		List<ProdutoDto> entradaInvalida = List.of(produto1);
		
		when(produtoService.criaProdutosEmLote(entradaInvalida)).thenThrow(new CodigoJaExisteException("Código já existente: PROD123"));
		
		
		mockMvc.perform(post("/api/produtos/lote")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(entradaInvalida)))
		.andExpect(status().isConflict())
		.andExpect(jsonPath("$.status").value(409))
		.andExpect(jsonPath("$.message").value("Código já existente: PROD123"))
		.andExpect(jsonPath("$.timestamp").exists());
	}

	@DisplayName("Retorna o produto pelo codigo pesquisado.")
	@Test
	public void deveRetornarOprodutoPeloCodigo() throws Exception{

		String codigo = "PROD001";

		LocalDateTime dataFixa = LocalDateTime.of(2024, 1, 1, 12, 0);

		ProdutoDto produtoEsperado = new ProdutoDto("PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.9"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);
		
		when(produtoService.procuraProdutoPeloCodigo(codigo)).thenReturn(produtoEsperado);
		
		mockMvc.perform(get("/api/produtos/{codigo}", codigo)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.codigo").value(produtoEsperado.codigo()))
		.andExpect(jsonPath("$.nome").value(produtoEsperado.nome()))
		.andExpect(jsonPath("$.preco").value(produtoEsperado.preco()));
	}

	@DisplayName("Lança uma exception quando o codigo nao existir no banco.")
	@Test
	public void deveRetornar404QuandoCodigoNaoExistir()throws Exception{
		
	    String codigoInvalido = "CODIGO_INEXISTENTE";

	    when(produtoService.procuraProdutoPeloCodigo(codigoInvalido)).thenThrow(new CodigoNaoExisteException());
	    
	    mockMvc.perform(get("/api/produtos/{codigo}", codigoInvalido)
	    		.contentType(MediaType.APPLICATION_JSON))
	    .andExpectAll(status().isNotFound())
	    .andExpect(jsonPath("$.message").value("O codigo do produto não existe!"))
	    .andExpect(jsonPath("$.status").value(404))
	    .andExpect(jsonPath("$.path").value("/api/produtos/" + codigoInvalido))
	    .andExpect(jsonPath("$.timestamp").exists());
		
	}
	
	@DisplayName("Retorna todos os produtos paginados.")
	@Test
	public void deveRetornarTodosOsProdutosPaginados()throws Exception{
				
		ProdutoDto produtoDto1 = new ProdutoDto("PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		ProdutoDto produtoDto2 = new ProdutoDto("PROD98831", "Fone Bluetooth teste",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		List<ProdutoDto> listaDeProdutos = List.of(produtoDto1, produtoDto2);
		
		Page<ProdutoDto> paginaDeProdutos = new PageImpl<>(listaDeProdutos, PageRequest.of(0, 10, Sort.by("name").ascending()), listaDeProdutos.size());
		
		when(produtoService.retornaTodosOsprodutos(any(Pageable.class))).thenReturn(paginaDeProdutos);
		
		mockMvc.perform(get("/api/produtos")
				.param("page", "0")
				.param("size", "10")
				.param("sort", "nome, asc")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content.length()").value(2))
		.andExpect(jsonPath("$.content[0].nome").value("Fone Bluetooth X500"))
		.andExpect(jsonPath("$.content[1].nome").value("Fone Bluetooth teste"))
		.andExpect(jsonPath("$.totalElements").value(2))
		.andExpect(jsonPath("$.totalPages").value(1))
		.andExpect(jsonPath("$.size").value(10))
		.andExpect(jsonPath("$.number").value(0));
	}
	
	@DisplayName("Retorna uma lista vazia se nao houver produtos.")
	@Test
	public void deveRetornarStatus500QuandoServiceFalharAoListarProdutos()throws Exception{
		
		Page<ProdutoDto> paginasDeProdutosVazia = Page.empty();
		
		when(produtoService.retornaTodosOsprodutos(any(Pageable.class))).thenReturn(paginasDeProdutosVazia);
		
		mockMvc.perform(get("/api/produtos")
				.param("page", "0")
				.param("size", "20")
				.param("sorte", "nome, asc")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content").isArray())
		.andExpect(jsonPath("$.content.length()").value(0))
		.andExpect(jsonPath("$.totalElements").value(0));
	}
	
	@DisplayName("Deleta um produto pelo codigo com sucesso.")
	@Test
	public void deveDeletarOProdutoPeloCodigo()throws Exception{
		
		String codigo = "1111111";
		
		doNothing().when(produtoService).deletaProdutoPeloCodigo(codigo);
		
		mockMvc.perform(delete("/api/produtos/{codigo}", codigo))
		.andExpect(status().isNoContent());
		
	}
	
	@DisplayName("Lança uma exception se o codigo nao existir.")
	@Test
	public void deveRetornarFalhaSeOCodigoNaoExistir()throws Exception{
		
		String codigoNaoExiste = "00000000";
		
		doThrow(new CodigoNaoExisteException()).when(produtoService).deletaProdutoPeloCodigo(codigoNaoExiste);
		
		mockMvc.perform(delete("/api/produtos/{codigo}", codigoNaoExiste))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.status").value(404))
		.andExpect(jsonPath("$.message").value("O codigo do produto não existe!"));

	}
	
	@DisplayName("DELETE - Lança uma exception se o codigo nao for passado na URL")
	@Test
	public void deveLancarExceptionCasoNaoSejaPassadoCodigNaUrl() throws Exception{
		
	    mockMvc.perform(delete("/api/produtos"))
        .andExpect(status().isMethodNotAllowed());
		
	}
	
	@DisplayName("PUT - Deve atualizar um produto pelo codigo com sucesso")
	@Test
	public void deveAtualizaProdutoPeloCodigoComSucesso() throws Exception{
		
		String codigo = "PROD98231";
		
		ProdutoDto dto = new ProdutoDto(codigo, "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		when(produtoService.atualizaProdutoPeloCodigo(codigo, dto)).thenReturn(dto);
		
		mockMvc.perform(put("/api/produtos/{codigo}", codigo)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.codigo").value("PROD98231"))
		.andExpect(jsonPath("$.nome").value("Fone Bluetooth X500"))
		.andExpect(jsonPath("$.preco").value(249.90))
		.andExpect(jsonPath("$.quantidadeEstoque").value(75))
		.andExpect(jsonPath("$.ativo").value(true));
	}
	
	@DisplayName("PUT- Deve lançar uma exception caso o corpo possua informações invalidas")
	@Test
	public void deveLancarExceptionCasoOCorpoTenhaInformacoesInvalidas() throws Exception{
		
		String codigo = "PROD98231";
		
		ProdutoDto dto = new ProdutoDto(codigo, "",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);
		
		mockMvc.perform(put("/api/produtos/{codigo}", codigo)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("Erro na validação"))
		.andExpect(jsonPath("$.errors[0].field").value("nome"))
		.andExpect(jsonPath("$.errors[0].message").value("O campo 'nome' é obrigatorio"));;
		
	}

	@DisplayName("PUT - Lança uma exception para caso o codigo esteja ausente na Url")
	@Test
	public void deveLancarExceptionCasoOCodigoDaUrlEstejaAusente()throws Exception{
		
		ProdutoDto dto = new ProdutoDto("PROD98231", "Fone Bluetooth X500",
				"Fones de ouvido sem fio com cancelamento de ruído e bateria de longa duração.",
				new BigDecimal("249.90"), 75, "Eletrônicos", "https://exemplo.com/imagens/fone-bluetooth.jpg", true);

		
		mockMvc.perform(put("/api/produtos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isMethodNotAllowed());
		
	}

}
