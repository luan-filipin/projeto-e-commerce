package com.br.e_commerce.pedidos_server.service;

import com.br.e_commerce.pedidos_server.dto.CriacaoPedidoDto;
import com.br.e_commerce.pedidos_server.dto.PedidoDto;

public interface PedidoService {

	CriacaoPedidoDto criaPedido(PedidoDto pedidoDto);
	
}
