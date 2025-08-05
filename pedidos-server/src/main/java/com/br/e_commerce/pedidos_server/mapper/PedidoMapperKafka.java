package com.br.e_commerce.pedidos_server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.e_commerce.pedidos_server.dto.kafka.ItemPedidoEvent;
import com.br.e_commerce.pedidos_server.dto.kafka.PedidoCriadoEvent;
import com.br.e_commerce.pedidos_server.entity.ItemPedido;
import com.br.e_commerce.pedidos_server.entity.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapperKafka {
	
	@Mapping(source = "id", target = "pedidoId")
	PedidoCriadoEvent toDto(Pedido entity);

    ItemPedidoEvent toDto(ItemPedido itemPedido);

}
