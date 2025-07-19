package br.com.e_commerce.produto_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@PostMapping("/produtos")
	public ResponseEntity<ProdutoRespostaCriacaoDto> criaProduto(@RequestBody @Valid ProdutoDto produtoDto) {
		 ProdutoRespostaCriacaoDto produtoSalvao = produtoService.criaProduto(produtoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvao);
	}
	
	@GetMapping("/produtos/{codigo}")
	public ResponseEntity<ProdutoDto> procuraProdutoPeloCodigo(@PathVariable @NotBlank String codigo){
		ProdutoDto produto = produtoService.procuraProdutoPeloCodigo(codigo);
		return ResponseEntity.ok(produto);
	}
}
