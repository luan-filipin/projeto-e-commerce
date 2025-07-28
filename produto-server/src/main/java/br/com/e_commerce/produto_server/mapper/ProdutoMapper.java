package br.com.e_commerce.produto_server.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.e_commerce.produto_server.dto.ProdutoDto;
import br.com.e_commerce.produto_server.entity.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

	Produto toEntity(ProdutoDto produtoDto);
	
	ProdutoDto toDto(Produto produto);
	
	List<Produto> toEntityList(List<ProdutoDto> produtosDto);
	
	List<ProdutoDto> toDtoList(List<Produto> produtos);
}
