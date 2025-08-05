package com.br.e_commerce.pedidos_server.mapper;

import org.mapstruct.Mapper;

import com.br.e_commerce.pedidos_server.dto.CriacaoItemPedidoDto;
import com.br.e_commerce.pedidos_server.dto.CriacaoPedidoDto;
import com.br.e_commerce.pedidos_server.dto.ItemPedidoDto;
import com.br.e_commerce.pedidos_server.dto.PedidoDto;
import com.br.e_commerce.pedidos_server.entity.ItemPedido;
import com.br.e_commerce.pedidos_server.entity.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
	
	Pedido toEntity(PedidoDto dto);
	PedidoDto toDto(Pedido entity);

	ItemPedido toEntity(ItemPedidoDto dto);
	ItemPedidoDto toDto(ItemPedido entity);
	
	
	CriacaoPedidoDto toCriacaoDto(Pedido entity);
	CriacaoItemPedidoDto toCriacaoItemDto(ItemPedido entity);
}
