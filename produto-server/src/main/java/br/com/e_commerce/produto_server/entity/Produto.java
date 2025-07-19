package br.com.e_commerce.produto_server.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "codigo", nullable = false, unique = true, updatable = false)
	private String codigo;
	
	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	@Column(name = "quantidade_estoque", nullable = false)
	private Integer quantidadeEstoque;
	
	@Column(nullable = false)
	private String categoria;
	
	@Column(name = "imagem_url", nullable = false)
	private String imagemUrl;
	
	@CreationTimestamp
	@Column(name = "criado", nullable = false)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	@Column(name = "atualizado", nullable = false)
	private LocalDateTime dataAtualizacao;
	
	@Column(nullable = false)
	private boolean ativo;
}
