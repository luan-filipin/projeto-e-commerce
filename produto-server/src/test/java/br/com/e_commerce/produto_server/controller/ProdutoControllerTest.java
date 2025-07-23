package br.com.e_commerce.produto_server.controller;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.hc.core5.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.e_commerce.produto_server.config.GlobalExceptionHandler;
import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.entity.Produto;
import br.com.e_commerce.produto_server.exception.CodigoNaoExisteException;
import br.com.e_commerce.produto_server.repository.ProdutoRepository;
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

	@Test
	void deveCriarProdutoQuandoARequisicaoForValida() throws Exception {

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

	@Test
	void deveRetornarErro400QuandoDadosObrigatoriosForemInvalidos() throws Exception {

		ProdutoDto produtoInvalido = new ProdutoDto("PROD001", "", "Descrição válida", new BigDecimal("99.90"), 10,
				"Categoria válida", "https://exemplo.com/imagem.jpg", true);

		mockMvc.perform(post("/api/produtos").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(produtoInvalido))).andExpect(status().isBadRequest());
	}

	@Test
	void deveRetornarOprodutoPeloCodigo() throws Exception{

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

	@Test
	void deveRetornar404QuandoCodigoNaoExistir()throws Exception{
		
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
}
