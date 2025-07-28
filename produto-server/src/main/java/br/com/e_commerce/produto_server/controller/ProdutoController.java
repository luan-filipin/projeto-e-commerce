package br.com.e_commerce.produto_server.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	//Cria um produto
	@PostMapping("/produtos")
	public ResponseEntity<ProdutoRespostaCriacaoDto> criaProduto(@RequestBody @Valid ProdutoDto produtoDto) {
		 ProdutoRespostaCriacaoDto produtoSalvao = produtoService.criaProduto(produtoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvao);
	}
	
	@PostMapping("/produtos/lote")
	public ResponseEntity<List<ProdutoDto>> criaProdutosEmLote(@RequestBody @Valid List<ProdutoDto> produtos){
		List<ProdutoDto> produtoCriado = produtoService.criaProdutosEmLote(produtos);		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
	}
	
	//Procura produto pelo codigo.
	@GetMapping("/produtos/{codigo}")
	public ResponseEntity<ProdutoDto> procuraProdutoPeloCodigo(@PathVariable @NotBlank String codigo){
		ProdutoDto produto = produtoService.procuraProdutoPeloCodigo(codigo);
		return ResponseEntity.ok(produto);
	}
	//Retornar todos os produtos paginados.
	@GetMapping("/produtos")
	public ResponseEntity<Page<ProdutoDto>> retornaTodosOsProdutos(
			@PageableDefault(page = 0, size = 20, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable){
		return ResponseEntity.ok(produtoService.retornaTodosOsprodutos(pageable));
	}
	//Deleta um produto pelo codigo.
	@DeleteMapping("/produtos/{codigo}")
	public ResponseEntity<Void> deletaProdutoPeloCodigo(@PathVariable @NotBlank String codigo) {
		produtoService.deletaProdutoPeloCodigo(codigo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
