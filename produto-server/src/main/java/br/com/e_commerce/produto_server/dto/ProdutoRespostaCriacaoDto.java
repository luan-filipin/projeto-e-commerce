package br.com.e_commerce.produto_server.dto;

import java.time.LocalDateTime;

public record ProdutoRespostaCriacaoDto(
		Long id,
		String nome,
		LocalDateTime dataCriacao) {

}
