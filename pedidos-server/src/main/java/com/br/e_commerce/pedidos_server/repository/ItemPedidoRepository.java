package com.br.e_commerce.pedidos_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.e_commerce.pedidos_server.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{

}
