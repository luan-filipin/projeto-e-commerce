package br.com.e_commerce.produto_server.dto.kafka;


public record ItemPedidoEvent(	
    String codigoProduto,
    Integer quantidade) {

}
