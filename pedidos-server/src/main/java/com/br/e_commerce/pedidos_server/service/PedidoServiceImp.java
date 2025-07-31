package com.br.e_commerce.pedidos_server.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.br.e_commerce.pedidos_server.dto.CriacaoItemPedidoDto;
import com.br.e_commerce.pedidos_server.dto.CriacaoPedidosDto;
import com.br.e_commerce.pedidos_server.dto.ItemPedidoDto;
import com.br.e_commerce.pedidos_server.dto.PedidoDto;
import com.br.e_commerce.pedidos_server.dto.ProdutoDto;
import com.br.e_commerce.pedidos_server.entity.ItemPedido;
import com.br.e_commerce.pedidos_server.entity.Pedido;
import com.br.e_commerce.pedidos_server.entity.StatusPedido;
import com.br.e_commerce.pedidos_server.entity.TipoMovimentacao;
import com.br.e_commerce.pedidos_server.mapper.PedidoMapper;
import com.br.e_commerce.pedidos_server.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PedidoServiceImp implements PedidoService{

	private final PedidoRepository pedidoRepository;
	private final PedidoMapper pedidoMapper;
	private final SolicitarProdutoService solicitarProdutoService;
	
	
	@Override
	public CriacaoPedidosDto criaPedido(PedidoDto pedidoDto) {
		
		// Cria o codigo do pedido.
		String codigo = pedidoDto.codigo() != null ? pedidoDto.codigo() : UUID.randomUUID().toString();
		
		Pedido pedido = pedidoMapper.toEntity(pedidoDto);
		pedido.setCodigo(codigo);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setStatus(StatusPedido.PENDENTE);
		
		BigDecimal total = BigDecimal.ZERO;
		List<ItemPedido> itens = new ArrayList<>();
		
		for(ItemPedidoDto itemDto: pedidoDto.itens()) {
			
			//Busca o produto.
			ProdutoDto produto = solicitarProdutoService.buscarProdutoPeloCodigo(itemDto.codigoProduto());
			System.out.println(produto);
			//Movimentar Estoque.
			solicitarProdutoService.movimentarEstoque(itemDto.codigoProduto(), itemDto.quantidade(), TipoMovimentacao.SAIDA);
			
			//Criar item.
			ItemPedido item = new ItemPedido();
			item.setCodigoProduto(produto.codigo());
			item.setNomeProduto(produto.nome());
			item.setPrecoUnitario(produto.preco());
			item.setQuantidade(itemDto.quantidade());
			item.setPedido(pedido);
			
			BigDecimal subtotal = produto.preco().multiply(BigDecimal.valueOf(itemDto.quantidade()));
			total = total.add(subtotal);
			
			itens.add(item);
			
		}
		
		pedido.setTotal(total);
		pedido.setItens(itens);
		
		Pedido pedidoSalvo = pedidoRepository.save(pedido);
		
		List<CriacaoItemPedidoDto> itensCriados = pedidoSalvo.getItens().stream()
				.map(i -> new CriacaoItemPedidoDto(
						i.getId(),
						i.getCodigoProduto(),
						i.getNomeProduto(),
						i.getPrecoUnitario(),
						i.getQuantidade(),
						i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade()))))
				.toList();
		
		return new CriacaoPedidosDto(pedidoSalvo.getId(),
				pedidoSalvo.getCodigo(),
				pedidoSalvo.getStatus(),
				pedidoSalvo.getTotal(), 
				itensCriados);
	}

}
