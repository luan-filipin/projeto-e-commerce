package com.br.e_commerce.pedidos_server.dto;

import java.math.BigDecimal;

public record ProdutoDto(
	    String codigo,
	    String nome,
	    BigDecimal preco,
	    Integer quantidadeEstoque
	) {}

