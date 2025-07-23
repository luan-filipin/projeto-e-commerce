package br.com.e_commerce.produto_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.e_commerce.produto_server.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	boolean existsByCodigo(String codigo);
	
	Optional<Produto> findByCodigo(String codigo);

	void deleteByCodigo(String codigo);
}
