package com.br.e_commerce.pedidos_server.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "codigo", nullable = false, unique = true, updatable = false)
	private String codigo;
	
	@Column(name = "usuario_login", nullable = false)
	private String usuarioLogin;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private StatusPedido status;
	
	@Column(name = "total", nullable = false)
	private BigDecimal total;
	
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
	private List<ItemPedido> itens;
	
}
