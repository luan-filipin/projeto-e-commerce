package com.br.e_commerce.pedidos_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.e_commerce.pedidos_server.dto.CriacaoPedidoDto;
import com.br.e_commerce.pedidos_server.dto.PedidoDto;
import com.br.e_commerce.pedidos_server.service.PedidoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PedidoController {
	
	private final PedidoService pedidoService;

	@PostMapping("/pedidos")
	public ResponseEntity<CriacaoPedidoDto> criaPedido(@RequestBody PedidoDto pedidoDto){
		CriacaoPedidoDto pedido = pedidoService.criaPedido(pedidoDto);		
		return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
	}
}
