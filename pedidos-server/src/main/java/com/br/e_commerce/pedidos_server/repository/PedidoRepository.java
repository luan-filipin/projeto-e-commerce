package com.br.e_commerce.pedidos_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.e_commerce.pedidos_server.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
