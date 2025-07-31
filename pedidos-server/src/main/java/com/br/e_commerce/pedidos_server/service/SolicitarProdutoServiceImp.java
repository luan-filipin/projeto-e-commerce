package com.br.e_commerce.pedidos_server.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.e_commerce.pedidos_server.dto.MovimentacaoEstoqueDto;
import com.br.e_commerce.pedidos_server.dto.ProdutoDto;
import com.br.e_commerce.pedidos_server.entity.TipoMovimentacao;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SolicitarProdutoServiceImp implements SolicitarProdutoService{

	private final RestTemplate restTemplate;
	private static final String BASE_URL= "http://localhost:8082/api/produtos";
	
	@Override
	public ProdutoDto buscarProdutoPeloCodigo(String codigo) {
		return restTemplate.getForObject(BASE_URL + "/" + codigo, ProdutoDto.class);
	}
	
	@Override
	public void movimentarEstoque(String codigo, Integer quantidade, TipoMovimentacao tipo) {
		MovimentacaoEstoqueDto dto = new MovimentacaoEstoqueDto(quantidade, tipo);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<MovimentacaoEstoqueDto> request = new HttpEntity<>(dto, headers);
		
		restTemplate.exchange(BASE_URL+"/estoque/movimentar/"+codigo, 
				HttpMethod.PUT,
				request,
				Void.class);
	}
}
