package br.com.e_commerce.produto_server.dto.kafka;

import java.util.List;


public record PedidoCriadoEvent(Long pedidoId, List<ItemPedidoEvent> itens) {

}
