package br.com.e_commerce.produto_server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.e_commerce.produto_server.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	boolean existsByCodigo(String codigo);
	
	Optional<Produto> findByCodigo(String codigo);

	void deleteByCodigo(String codigo);
	
	@Query("SELECT p.codigo FROM Produto p WHERE p.codigo IN :codigos")
	List<String> findCodigosExistentes(List<String> codigos);

}
