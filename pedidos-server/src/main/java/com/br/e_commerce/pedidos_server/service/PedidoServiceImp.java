package com.br.e_commerce.pedidos_server.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.e_commerce.pedidos_server.dto.CriacaoPedidoDto;
import com.br.e_commerce.pedidos_server.dto.PedidoDto;
import com.br.e_commerce.pedidos_server.dto.kafka.PedidoCriadoEvent;
import com.br.e_commerce.pedidos_server.entity.ItemPedido;
import com.br.e_commerce.pedidos_server.entity.Pedido;
import com.br.e_commerce.pedidos_server.entity.StatusPedido;
import com.br.e_commerce.pedidos_server.kafka.PedidoProducerService;
import com.br.e_commerce.pedidos_server.mapper.PedidoMapper;
import com.br.e_commerce.pedidos_server.mapper.PedidoMapperKafka;
import com.br.e_commerce.pedidos_server.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PedidoServiceImp implements PedidoService {

	private final PedidoRepository pedidoRepository;
	private final PedidoMapper pedidoMapper;
	private final PedidoProducerService pedidoProducerService;
	private final PedidoMapperKafka pedidoMapperKafka;

	@Override
	public CriacaoPedidoDto criaPedido(PedidoDto pedidoDto) {
		
		Pedido pedido = new Pedido();
		pedido.setUsuarioLogin(pedidoDto.usuarioLogin());
		pedido.setStatus(StatusPedido.PENDENTE);
		pedido.setDataCriacao(LocalDateTime.now());
		
		List<ItemPedido> itens = pedidoDto.itens().stream()
				.map(itemDto -> {
					ItemPedido item = new ItemPedido();
					item.setCodigoProduto(itemDto.codigoProduto());
					item.setPrecoUnitario(itemDto.precoUnitario());
					item.setQuantidade(itemDto.quantidade());
					item.setPedido(pedido);
					return item;
				}).toList();
		
		pedido.setItens(itens);
		pedido.setTotal(BigDecimal.ZERO);
		
		Pedido pedidoSalvo = pedidoRepository.save(pedido);
		
		PedidoCriadoEvent evento = pedidoMapperKafka.toDto(pedidoSalvo);
		pedidoProducerService.enviarPedidoCriado(evento);
		
		return pedidoMapper.toCriacaoDto(pedidoSalvo);

	}
}
