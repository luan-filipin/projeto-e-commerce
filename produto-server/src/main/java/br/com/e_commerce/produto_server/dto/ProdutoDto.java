package br.com.e_commerce.produto_server.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDto(
		@NotBlank(message = "O campo 'codigoProduto' é obrigatorio") String codigo,
		@NotBlank(message = "O campo 'nome' é obrigatorio") String nome,
		@NotBlank(message = "O campo 'descrição' é obrigatorio") String descricao,
		@NotNull(message = "O campo 'preco' é obrigatorio")BigDecimal preco,
		@NotNull(message = "O campo 'quantidadeEstoque' é obrigatorio")Integer quantidadeEstoque,
		@NotBlank(message = "O campo 'categoria' é obrigatorio")String categoria,
		@NotBlank(message = "O campo 'imageUrl' é obrigatorio")String imageUrl,
		@NotNull(message = "O campo 'ativo' é obrigatório") Boolean ativo) {
}
