package com.br.e_commerce.pedidos_server.service;

import com.br.e_commerce.pedidos_server.dto.CriacaoPedidosDto;
import com.br.e_commerce.pedidos_server.dto.PedidoDto;

public interface PedidoService {

	CriacaoPedidosDto criaPedido(PedidoDto pedidoDto);
	
}
