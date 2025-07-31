package com.br.e_commerce.pedidos_server.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.br.e_commerce.pedidos_server.dto.CriacaoPedidosDto;
import com.br.e_commerce.pedidos_server.dto.ItemPedidoDto;
import com.br.e_commerce.pedidos_server.dto.PedidoDto;
import com.br.e_commerce.pedidos_server.entity.ItemPedido;
import com.br.e_commerce.pedidos_server.entity.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
	
	Pedido toEntity(PedidoDto pedidoDto);
	CriacaoPedidosDto toDto(Pedido entity);
	
	List<ItemPedidoDto> toDoList(List<Pedido> pedidos);
	
	ItemPedidoDto toEntity(ItemPedido entity);
	ItemPedido toDto(ItemPedidoDto dto);

}
