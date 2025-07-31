package com.br.e_commerce.pedidos_server.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "codigo_produto", nullable = false)
	private String codigoProduto;
	
	@Column(name = "nome_produto", nullable = false)
	private String nomeProduto;
	
	@Positive
	@Column(name = "preco_unitario", nullable = false)
	private BigDecimal precoUnitario;
	
	@Min(1)
	@Column(name = "quantidade", nullable = false)
	private Integer quantidade;
	
	@ManyToOne
	@JoinColumn(name ="pedido_id")
	private Pedido pedido;
}
